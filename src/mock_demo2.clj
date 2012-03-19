(ns mock_demo2
  (:use clojure.test))

(def mock-calls (atom {}))

(defn stub-fn [the-function return-value]
  (swap! mock-calls assoc the-function [])
  (fn [& args]
    (swap! mock-calls update-in [the-function] conj args)
  return-value))

(defn mock-fn [the-function]
  (stub-fn the-function nil))

(defmacro verify-call-times-for [fn-name number]
  `(is (= ~number (count (@mock-calls ~(keyword fn-name))))))

(defmacro verify-first-call-args-for [fn-name & args]
  `(verify-nth-call-args-for 1 ~fn-name ~@args))

(defmacro verify-nth-call-args-for [n fn-name & args]
  `(is (= '~args (nth (@mock-calls ~(keyword fn-name)) (dec ~n)))))

(defmacro mocking [fn-names & body]
  (let [mocks (map #(list 'mock-fn (keyword %)) fn-names)]
  `(binding [~@(interleave fn-names mocks)] ~@body)))

(defmacro stubbing [stub-forms & body]
  (let [stub-pairs (partition 2 stub-forms)
    real-fns (map first stub-pairs)
    returns (map last stub-pairs)
    stub-fns (map #(list 'stub-fn %1 %2) real-fns returns)]
  `(binding [~@(interleave real-fns stub-fns)] ~@body)))

(defn clear-calls []
  (reset! mock-calls {}))

(defmacro defmocktest [test-name & body]
  `(deftest ~test-name
    (binding [mock-calls (atom {})]
      (do ~@body))))

(defmocktest test-fetch-expenses-greater-than
  (stubbing [fetch-all-expenses all-expenses]
    (let [filtered (fetch-expenses-greater-than "" "" "" 15.0)]
      (is (= (count filtered) 2))
      (is (= (:amount (first filtered)) 20.0))
      (is (= (:amount (last filtered)) 30.0)))))

(defmocktest test-filter-greater-than
  (mocking [log-call]
    (let [filtered (expenses-greater-than all-expenses 15.0)]
      (is (= (count filtered) 2))
      (is (= (:amount (first filtered)) 20.0))
      (is (= (:amount (last filtered)) 30.0)))
    (verify-call-times-for log-call 1)
    (verify-first-call-args-for log-call "expenses-greater-than" 15.0))

(ns mock_demo
  (:import (java.text SimpleDateFormat)
           (java.util Calendar GregorianCalendar))
  (:use clojure.contrib.str-utils)
  (:use clojure.test))
 
(defn stub-fn [the-function return-value]
  (swap! mock-calls assoc the-function [])
  (fn [& args]
    (swap! mock-calls update-in [the-function] conj args)
    return-value))

(defn mock-fn [the-function]
  (stub-fn the-function nil))

(defmacro mocking [fn-names & body]
  (let [mocks (map #(list 'mock-fn (keyword %)) fn-names)]
  `(binding [~@(interleave fn-names mocks)] ~@body)))

(defmacro stubbing2 [stub-forms & body]
  (let [stub-pairs (partition 2 stub-forms)
      real-fns (map first stub-pairs)
      returns (map last stub-pairs)
      stub-fns (map #(list 'stub-fn %1 %2) real-fns returns)]
    `(binding [~@(interleave real-fns stub-fns)] ~@body)))

(defmacro stubbing [stub-forms & body]
  (let [stub-pairs (partition 2 stub-forms)
        returns (map last stub-pairs)
        stub-fns (map #(list 'constantly %) returns)
        real-fns (map first stub-pairs)]
  `(binding [~@(interleave real-fns stub-fns)] ~@body)))

(defstruct expense :amount :date)

(defn log-call [id & args]
  (println "Audit - called" id "with:" (str-join ", " args))
  ;;do logging to some audit data-store
)
(defn ^:dynamic fetch-all-expenses [username start-date end-date]
  (log-call "fetch-all" username start-date end-date)
  ;find in data-store, return list of expense structs
)
(defn expenses-greater-than [expenses threshold]
  (log-call "expenses-greater-than" threshold)
  (filter #(> (:amount %) threshold) expenses))

(defn fetch-expenses-greater-than [username start-date end-date threshold]
  (let [all (fetch-all-expenses username start-date end-date)]
    (expenses-greater-than all threshold)))

(def all-expenses [(struct-map expense :amount 10.0 :date "2010-02-28")
  (struct-map expense :amount 20.0 :date "2010-02-25")
  (struct-map expense :amount 30.0 :date "2010-02-21")])

(deftest test-fetch-expenses-greater-than
  (stubbing [fetch-all-expenses all-expenses]
    (let [filtered (fetch-expenses-greater-than "" "" "" 15.0)]
      (is (= (count filtered) 2))
      (is (= (:amount (first filtered)) 20.0))
      (is (= (:amount (last filtered)) 30.0)))))

(def mock-calls (atom {}))

(run-tests 'mock_demo)
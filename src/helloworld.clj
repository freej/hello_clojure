(ns helloworld
  (:gen-class))
 
(defn prime-number [] 
  (reduce
    (fn [primes number]
      (if (some zero? (map (partial mod number) primes))
      primes
      (conj primes number)))
    [2]
    (take 1000 (iterate inc 3))))

(defn char-cnt [s]
  "Counts occurence of each character in s"
  (reduce
    (fn [m k]
      (update-in m [k] (fnil inc 0)))
  {}
  (seq s)))

(defn str-cnt [s] 
 (reduce #(assoc %1 %2 (inc (%1 %2 0))) {} (re-seq #"\w+" s)))

(defn -main [& args]
  (println "Hello world!"))
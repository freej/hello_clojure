(ns fibo)
(defn lazy-seq-fibo
 ([]
 (concat [0 1] (lazy-seq-fibo 0 1)))
 ([a b]
 (let [n (+ a b)]
 (lazy-seq
 (cons n (lazy-seq-fibo b n))))))

(take 10 (lazy-seq-fibo))

(defn fibo []
(map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

(take 10 (fibo))

(def lots-o-fibs (take 1000000000 (fibo)))

;;(nth lots-o-fibs 100)
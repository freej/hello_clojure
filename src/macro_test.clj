(ns macro_test)
(defmacro around-zero [number negative-expr zero-expr positive-expr]
`(let [number# ~number] ; so number is only evaluated once
(cond
(< (Math/abs number#) 1e-15) ~zero-expr
(pos? number#) ~positive-expr
true ~negative-expr)))
  
(around-zero 0.1 (println "-") (println "0") (println "+"))

(println (around-zero 0.1 "-" "0" "+"))

(around-zero 0.1
(do (log "really cold!") (println "-"))
(println "0")
(println "+"))

(defn number-category [number]
(around-zero number "negative" "zero" "positive"))

(println (number-category -0.1)) ; -> negative
(println (number-category 0)) ; -> zero
(println (number-category 0.1)) ; -> positive
(ns demo1)
;;(use 'clojure.set)
;;(def languages #{"java" "c" "d" "clojure" })
;;(def letters #{"a" "b" "c" "d" "e" })
;;(def beverages #{"java" "chai" "pop" })
;;(union languages beverages)
;;(difference languages beverages)
;;(intersection languages beverages)
;;(select #(= 1 (.length %)) languages)

(def compositions
#{{:name "The Art of the Fugue" :composer "J. S. Bach" }
{:name "Musical Offering" :composer "J. S. Bach" }
{:name "Requiem" :composer "Giuseppe Verdi" }
{:name "Requiem" :composer "W. A. Mozart" }})
(def composers
#{{:composer "J. S. Bach" :country "Germany" }
{:composer "W. A. Mozart" :country "Austria" }
{:composer "Giuseppe Verdi" :country "Italy" }})
(def nations
#{{:nation "Germany" :language "German" }
{:nation "Austria" :language "German" }
{:nation "Italy" :language "Italian" }})

(rename compositions {:name :title})
(select #(= (:name %) "Requiem") compositions)
(project compositions [:name])
;;(for [m compositions c composers] (concat m c))
(join compositions composers)
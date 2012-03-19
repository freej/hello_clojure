(ns record_deme
  (:require
    [clj-record.core :as core]
    [clj-record.query :as query]
    [record.users :as users]
    [record.charges :as charges]))

(users/create {:login "freej"
  :password "none"
  :email_address "freej@sohu.com" 
  :creation_time "2012-03-17 20:35:00"})

(def current-id (map :id (users/find-records {:email_address "freej@sohu.com"})))

(defn update-user-login [new-login value] (users/update {:login new-login :id value}))

(reduce update-user-login "hyperj" current-id)

(map #(users/destroy-record {:id %}) current-id)

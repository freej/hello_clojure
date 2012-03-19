(ns record.config
    (:require clj-record.boot))

(def db
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql" 
   :user "root"
   :password "haolin"
   :subname "//localhost/my_test"})

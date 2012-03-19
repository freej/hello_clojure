(ns record.users
  (:require clj-record.boot)
  (:use record.config))

 (clj-record.core/init-model
    :table-name "users")

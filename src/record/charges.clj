(ns record.charges)
  (:require clj-record.boot)
  (:use record.config))

 (clj-record.core/init-model
    :table-name "charges")

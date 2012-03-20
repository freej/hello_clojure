(ns redis_demo
  (:refer-clojure :exclude [keys type get set sort])
  (:require [redis.core :as redis]))

(redis/with-server {:host "127.0.0.1" :port 6379 :db 0}
  (redis/ping))

(redis/with-server {:host "127.0.0.1" :port 6379 :db 0}
  (redis/set "name" "deepthi"))

(redis/with-server {:host "127.0.0.1" :port 6379 :db 0}
  (redis/get "name" ))

(redis/with-server {:host "127.0.0.1" :port 6379 :db 0}
  (redis/rpush "names" "adi"))

(redis/with-server {:host "127.0.0.1" :port 6379 :db 0}
  (redis/rpush "names" "punit"))
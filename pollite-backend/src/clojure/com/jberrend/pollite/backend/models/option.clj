(ns com.jberrend.pollite.backend.models.option
  (:import (org.jdbi.v3.core.mapper RowMapper)
           (java.sql Date)
           (java.util UUID))
  (:gen-class))

;; TODO: Do we need to have the updated at time?
(defrecord Option
  [^Integer id
   ^Integer poll_id
   ^String uuid
   ^String text
   ^Date created_at])

(deftype OptionMapper
  []
  RowMapper ; extends the interface

  ;; implement the map method declared in the RowMapper interface
  ;; map in java takes 2 arguments, the first argument here is a reference to 'this'
  (map [_ rs _] (Option. (.getInt rs "id")
                         (.getInt rs "poll_id")
                         (.getString rs "uuid")
                         (.getString rs "text")
                         (.getDate rs "created_at"))))

(defn new-option [poll-id text]
  (Option. nil poll-id (str (UUID/randomUUID)) text nil))

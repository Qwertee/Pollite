(ns com.jberrend.pollite.backend.models.option
  (:import (org.jdbi.v3.core.mapper RowMapper)
           (java.sql Date))
  (:gen-class))

;; TODO: Do we need to updated at time?
(defrecord Option
  [^Integer id
   ^Integer poll_id
   ^String text
   ^Date created_at
   ^Date updated_at])

(deftype OptionMapper
  []
  RowMapper
  (map [_ rs _] (->Option (.getInt rs "id")
                          (.getInt rs "poll_id")
                          (.getString rs "text")
                          (.getDate rs "created_at")
                          (.getDate rs "updated_at"))))
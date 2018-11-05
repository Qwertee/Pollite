(ns com.jberrend.pollite-backend.models.option
  (:import (org.jdbi.v3.core.mapper RowMapper)
           (java.sql Date))
  (:gen-class))

(defrecord Option
  [^Integer id
   ^Integer poll-id
   ^String text
   ^Date created-at
   ^Date updated-at])

(deftype OptionMapper
  []
  RowMapper
  (map [_ rs _] (Option. (.getInt rs "id")
                         (.getInt rs "poll_id")
                         (.getString rs "text")
                         (.getDate rs "created_at")
                         (.getDate rs "updated_at"))))
(ns com.jberrend.pollite.backend.models.poll
  (:gen-class)
  (:import (java.sql Date)
           (org.jdbi.v3.core.mapper RowMapper)))

;; TODO: Do we need the updated at time?
(defrecord Poll
  [^Integer id
   ^String prompt
   ^String hash
   ^Date created_at
   ^Date updated_at])

(deftype PollMapper
  []
  RowMapper
  (map [_ rs _] (->Poll (.getInt rs "id")
                        (.getString rs "prompt")
                        (.getString rs "hash")
                        (.getDate rs "created_at")
                        (.getDate rs "updated_at"))))
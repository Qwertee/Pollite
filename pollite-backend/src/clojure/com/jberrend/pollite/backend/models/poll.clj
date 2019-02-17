(ns com.jberrend.pollite.backend.models.poll
  (:gen-class)
  (:import (java.sql Date)
           (org.jdbi.v3.core.mapper RowMapper)))

;; TODO: Do we need the updated at time?
(defrecord Poll
  [^Integer id
   ^String prompt
   ^String uuid
   ^Date created_at])

(deftype PollMapper
  []
  RowMapper
  (map [_ rs _] (Poll. (.getInt rs "id")
                       (.getString rs "prompt")
                       (.getString rs "uuid")
                       (.getDate rs "created_at"))))

(defn new-poll [prompt]
  (let [uuid (java.util.UUID/randomUUID)]
    (Poll. nil prompt uuid nil)))

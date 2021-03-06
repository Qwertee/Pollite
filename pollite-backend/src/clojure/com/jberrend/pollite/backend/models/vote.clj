(ns com.jberrend.pollite.backend.models.vote
  (:import (java.sql Date)
           (org.jdbi.v3.core.mapper RowMapper)
           (java.util UUID)))

(defrecord Vote
  [^Integer id
   ^Integer option_id
   ^String uuid
   ^String fingerprint
   ^Date created_at])

(deftype VoteMapper
  []
  RowMapper
  (map [_ rs _] (Vote. (.getInt rs "id")
                       (.getInt rs "option_id")
                       (.getString rs "uuid")
                       (.getString rs "fingerprint")
                       (.getDate rs "created_at"))))

(defn new-vote [option-id fingerprint]
  (Vote. nil option-id (str (UUID/randomUUID)) fingerprint nil))

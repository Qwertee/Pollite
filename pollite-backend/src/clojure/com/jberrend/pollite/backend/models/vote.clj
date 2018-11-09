(ns com.jberrend.pollite.backend.models.vote
  (:import (java.sql Date)
           (org.jdbi.v3.core.mapper RowMapper)))

(defrecord Vote
  [^Integer id
   ^Integer option_id
   ^String hash
   ^String address
   ^Date created_at])

(deftype VoteMapper
  []
  RowMapper
  (map [_ rs _] (Vote. (.getInt rs "id")
                       (.getInt rs "option_id")
                       (.getString rs "hash")
                       (.getString rs "address")
                       (.getDate rs "created_at"))))
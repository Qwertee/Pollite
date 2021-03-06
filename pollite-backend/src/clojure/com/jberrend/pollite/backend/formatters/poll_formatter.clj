(ns com.jberrend.pollite.backend.formatters.poll-formatter
  (:require [com.jberrend.pollite.backend.db :as db])
  (:import (com.jberrend.pollite.backend.models.poll PollMapper)
           (com.jberrend.pollite.backend.models.option OptionMapper)
           (com.jberrend.pollite.backend.models.vote VoteMapper)))

(defn- count-votes [option votes]
  (count (filter #(= (:option_id %) (:id option)) votes)))

(defn format-options [options votes]
  (into []
        (for [o options]
          {"text" (:text o)
           "uuid" (:uuid o)
           "votes" (count-votes o votes)})))

(defn format-poll-response
  "Finds the poll, options, and votes from a given poll uuid and returns the formatted map"
  [uuid]
  (let [poll (db/select-only PollMapper (str "SELECT * FROM poll WHERE uuid='" uuid "'"))

        options (db/select OptionMapper (str "SELECT * FROM option WHERE poll_id="
                                             (:id poll)))

        ; not the prettiest
        votes (db/select VoteMapper (str "SELECT * FROM vote WHERE option_id IN"
                                         "(SELECT option_id FROM option WHERE poll_id="
                                         (:id poll)
                                         ")"))]
    {"prompt" (:prompt poll)
     "options" (format-options options votes)}))

(defn format-vote-response
  "Wrapper around format-poll-response which also includes a status for whether a vote attempt was
   successful or not."
  [poll-uuid
   vote-success]
  {"poll" (format-poll-response poll-uuid)
   "voteSuccess" vote-success})

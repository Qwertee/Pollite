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
           "votes" (count-votes o votes)})))

(defn format-response
  "Finds the poll, options, and votes from a given poll hash and returns the formatted json"
  [hash]
  (let [poll (first (db/select PollMapper (str "SELECT * FROM poll WHERE hash='" hash "'")))

        options (db/select OptionMapper (str "SELECT * FROM option WHERE poll_id="
                                             (:id poll)))

        ; not the prettiest
        votes (db/select VoteMapper (str "SELECT * FROM vote WHERE option_id IN"
                                         "(SELECT option_id FROM option WHERE poll_id="
                                         (:id poll)
                                         ")"))]
    {"prompt" (:prompt poll)
     "options" (format-options options votes)}))

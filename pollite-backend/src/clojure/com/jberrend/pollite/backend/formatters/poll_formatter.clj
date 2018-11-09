(ns com.jberrend.pollite.backend.formatters.poll-formatter
  (:require [com.jberrend.pollite.backend.db :as db])
  (:import (com.jberrend.pollite.backend.models.poll PollMapper)
           (com.jberrend.pollite.backend.models.option OptionMapper)
           (com.jberrend.pollite.backend.models.vote VoteMapper)))

(defn format-response
  "Finds the poll, options, and votes from a given poll hash and returns the formatted json"
  [hash]
  (let [poll (first (db/select PollMapper (str "SELECT * FROM poll WHERE hash='" hash "'")))
        options (db/select OptionMapper (str "SELECT * FROM option WHERE poll_id=" (:id poll)))
        votes (db/select VoteMapper (str "SELECT * FROM vote WHERE option_id=" (:id poll)))]
    {"prompt" (:prompt poll)
     "option" (:text (first options))
     "vote" (:id (first votes))}))
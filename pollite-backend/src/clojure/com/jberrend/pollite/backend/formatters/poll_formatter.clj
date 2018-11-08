(ns com.jberrend.pollite.backend.formatters.poll-formatter
  (:require [com.jberrend.pollite.backend.db :as db])
  (:import (com.jberrend.pollite.backend.models.poll PollMapper)))

(defn format-response
  "Finds the poll, options, and votes from a given poll hash and returns the formatted json"
  [hash]
  (let [poll (first (db/select PollMapper (str "SELECT * FROM poll WHERE hash='" hash "'")))]
    (:prompt poll)))
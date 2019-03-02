(ns com.jberrend.pollite.backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [com.jberrend.pollite.backend.formatters.poll-formatter :as poll-formatter]
            [com.jberrend.pollite.backend.db :as db]
            [com.jberrend.pollite.backend.models.poll :as poll]
            [com.jberrend.pollite.backend.models.option :as option]
            [com.jberrend.pollite.backend.models.vote :as vote]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]])
  (:gen-class)
  (:import (com.jberrend.pollite.backend.models PollDao OptionDao VoteDao)
           (com.jberrend.pollite.backend.models.poll PollMapper)
           (com.jberrend.pollite.backend.models.option OptionMapper)
           (com.jberrend.pollite.backend.models.vote VoteMapper)))

;; TODO: are the CORS headers really needed? site seems to work with them, so leave for now
(def json-response-headers {"Content-Type" "application/json; charset=utf-8"
                            "Access-Control-Allow-Origin" "*"
                            "Access-Control-Allow-Headers" "Content-Type"
                            "Access-Control-Allow-Methods" "GET,POST,OPTIONS"})

;(defn parse-int [str]
;  (Integer. (re-find #"[0-9]*" str)))

(defn process-new-poll-payload
  "Takes the given request payload (sent by client) and inserts a new poll and its options into
   the database"
  [request-payload]
  (let [p (poll/new-poll (str (get request-payload "prompt")))]
    (db/insert p PollDao)
    (let [poll (db/select-only PollMapper (str "SELECT * FROM poll WHERE uuid='" (:uuid p) "'"))
          poll-id (:id poll)]
      (doseq [opt (get request-payload "options")]
        (db/insert (option/new-option poll-id opt) OptionDao)))
    ;; TODO: return something other than uuid?
    (:uuid p)))

(defn submit-vote
  ""
  [uuid fingerprint]
  (let [o (db/select-only OptionMapper (str "SELECT * FROM option WHERE uuid='" uuid "'"))

        ;; get the poll for the option we want to submit a vote for.
        p (db/select-only PollMapper (str "SELECT * FROM poll WHERE id='" (:poll_id o) "'"))

        ;; get all the votes for all the options for the relevant poll that have the same fingerprint
        votes (db/select VoteMapper (str "SELECT * FROM vote WHERE option_id IN "
                                         "(SELECT option_id FROM option WHERE poll_id='" (:id p) "')"
                                         "AND fingerprint='" fingerprint "'"))]
    ;; if more than zero votes exist return error
    (if (empty? votes)
      (do
        (db/insert (vote/new-vote (:id o) fingerprint) VoteDao)
        (poll-formatter/format-vote-response (:uuid p) true))
      (poll-formatter/format-vote-response (:uuid p) false))))

(defroutes app-routes
  (GET "/" []
    {:status 200
     :headers json-response-headers
     :body (json/write-str {:a "some"
                            :b "json"})})

  ;; Should return all necessary info for frontend to display poll
  (GET "/poll/:uuid" uuid
    {:status  200
     :headers json-response-headers
     :body    (json/write-str                          ; format the json map
               (poll-formatter/format-poll-response    ; generate response json
                (-> uuid :route-params :uuid)))})

  (OPTIONS "/new/poll" []
    {:headers json-response-headers})

  (POST "/new/poll" params
    {:status  200
     :headers json-response-headers
     :body    (json/write-str {:uuid (process-new-poll-payload (:body params))})})

  (POST "/vote" params
    (let [body (:body params)
          uuid (get body "optionUuid")
          fingerprint (get body "fingerprint")]

      ;; need to make sure that the vote is legitimate (mainly that the fingerprint hasn't already
      ;; voted in the poll.
      {:status  200
       :headers json-response-headers
       :body    (json/write-str (submit-vote uuid fingerprint))}))

  (route/not-found "Not Found"))

(defn init []
  (db/initialize))

;(def app
;  (wrap-defaults app-routes api-defaults))

(def app
  (wrap-json-body app-routes))

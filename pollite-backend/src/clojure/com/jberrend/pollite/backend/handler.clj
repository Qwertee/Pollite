(ns com.jberrend.pollite.backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [com.jberrend.pollite.backend.formatters.poll-formatter :as poll-formatter]
            [com.jberrend.pollite.backend.db :as db]
            [com.jberrend.pollite.backend.models.poll :as poll]
            [com.jberrend.pollite.backend.models.option :as option]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]])
  (:gen-class)
  (:import (com.jberrend.pollite.backend.models PollDao OptionDao)
           (com.jberrend.pollite.backend.models.poll PollMapper)))

;; TODO: are the CORS headers really needed? site seems to work with them, so leave for now
(def json-response-headers {"Content-Type" "application/json; charset=utf-8"
                            "Access-Control-Allow-Origin" "*"
                            "Access-Control-Allow-Headers" "Content-Type"
                            "Access-Control-Allow-Methods" "GET,POST,OPTIONS"})

;(defn parse-int [str]
;  (Integer. (re-find #"[0-9]*" str)))

(defn process-new-poll-payload [request-payload]
  "Takes the given request payload (sent by client) and inserts a new poll and its options into the database"
  (let [p (poll/new-poll (str (get request-payload "pollName")))]
    (db/insert p PollDao)
    (let [poll (first (db/select PollMapper (str "SELECT * FROM poll WHERE uuid='" (:uuid p) "'")))
          poll-id (:id poll)]
      (doseq [opt (get request-payload "pollOptions")]
        (db/insert (option/new-option poll-id opt) OptionDao)))
    (:uuid p)))



(defroutes app-routes
           (GET "/" []
             {:status 200
              :headers json-response-headers
              :body (json/write-str{:a "some"
                                    :b "json"})})

           ;; Should return all necessary info for frontend to display poll
           (GET "/poll/:uuid" uuid
             {:status  200
              :headers json-response-headers
              :body    (json/write-str                      ; format the json map
                         (poll-formatter/format-response    ; generate response json
                           (-> uuid :route-params :uuid)))})

           (OPTIONS "/new/poll" []
             {:headers json-response-headers})

           (POST "/new/poll" params
             {:status  200
              :headers json-response-headers
              :body    (json/write-str (poll-formatter/format-response (process-new-poll-payload (:body params))))})

           (route/not-found "Not Found"))


(defn init []
  (db/initialize))

;(def app
;  (wrap-defaults app-routes api-defaults))

(def app
  (wrap-json-body app-routes))

(ns com.jberrend.pollite.backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [com.jberrend.pollite.backend.formatters.poll-formatter :as poll-formatter]
            [com.jberrend.pollite.backend.db :as db]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:gen-class))

(def json-response-headers {"Content-Type" "application/json; charset=utf-8"})

;(defn parse-int [str]
;  (Integer. (re-find #"[0-9]*" str)))

(defroutes app-routes
           (GET "/" []
             {:status 200
              :headers json-response-headers
              :body (json/write-str{:a "some"
                                    :b "json"})})

           ;; Should return all necessary info for frontend to display poll
           (GET "/poll/:hash" hash
             {:status  200
              :headers json-response-headers
              :body    (json/write-str                      ; format the json map
                         (poll-formatter/format-response    ; generate response json
                           (-> hash :route-params :hash))
                         :key-fn #(do
                                    (if (.contains % "option-")
                                      %
                                      %)))})
           (route/not-found "Not Found"))


(defn init []
  (db/initialize))

(def app
  (wrap-defaults app-routes site-defaults))

(ns pollite-backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
           (GET "/" []
             {:status 200
              :headers {"Content-Type" "application/json; charset=utf-8"}
              :body (json/write-str{:a "some"
                                    :b "json"})})
           (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

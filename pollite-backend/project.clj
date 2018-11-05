(defproject pollite-backend "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [org.jdbi/jdbi3-core "3.5.1"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/data.json "0.2.6"]
                 [honeysql "0.9.4"]
                 [mysql/mysql-connector-java "8.0.13"]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler pollite-backend.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
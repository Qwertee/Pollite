(defproject pollite-backend "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [org.jdbi/jdbi3-core "3.5.1"]
                 [org.jdbi/jdbi3-sqlobject "3.5.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/data.json "0.2.6"]
                 [honeysql "0.9.4"]
                 [mysql/mysql-connector-java "8.0.13"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]

  ;; need to compile the models before the db so dependencies aren't messed up
  ;; (is there seriously no better way to do this?????)
  :prep-tasks ["javac" ["compile" "com.jberrend.pollite.backend.models.option"]
                       ["compile" "com.jberrend.pollite.backend.models.poll"]
                       ["compile" "com.jberrend.pollite.backend.models.vote"]]
  :plugins [[lein-ring "0.12.4"]]
  :aot :all
  :ring {:init com.jberrend.pollite.backend.handler/init
         :handler com.jberrend.pollite.backend.handler/app
         :nrepl {:start? true
                 :port 9998}}                               ; can connect repl to running server at this port!
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})

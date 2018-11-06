(ns com.jberrend.pollite_backend.db
  (:require [honeysql.core :as sql])
  (:import (org.jdbi.v3.core Jdbi Handle)
           (org.jdbi.v3.sqlobject SqlObjectPlugin)
           (com.jberrend.pollite_backend.models OptionDao))
  (:gen-class))

(def ds (Jdbi/create "jdbc:mysql://167.99.235.49:3306/pollite_test" "pollite_user" "password"))

(defn initialize []
  (.installPlugin ds (SqlObjectPlugin.)))

(defmacro select
  "executes the given query map, placing the matching rows into
   objects as determined by the passed mapper type

   ex. (select OptionMapper 'select * from option'"
  [mapper query]
  `(let [handle# (.open ds)]
     (-> handle#
         (.createQuery (first (sql/format ~query)))
         (.map (new ~mapper)))))

(defn insert-option [option]
  (let [handle (.open ds)
        dao (.attach handle OptionDao)]
    (-> dao
        (.insertOption option))))

(def option {"poll-id" 0
             "text" "this is an option MAP"
             "created_at" nil
             "updated_at" nil})

;; works
(defn insert-test []
  (insert-option option))


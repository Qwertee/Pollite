(ns com.jberrend.pollite_backend.db
  (:require [honeysql.core :as sql])
  (:import (org.jdbi.v3.core Jdbi Handle)
           (org.jdbi.v3.sqlobject SqlObjectPlugin)
           (com.jberrend.pollite_backend.models OptionDao)
           (com.jberrend.pollite_backend.models.option Option))
  (:gen-class))

(def ds (Jdbi/create "jdbc:mysql://167.99.235.49:3306/pollite_test" "pollite_user" "password"))

(defn initialize
  "Initializes the necessary plugins for JDBI before any transactions can take place"
  []
  (.installPlugin ds (SqlObjectPlugin.)))

(defn map-key->string-key
  "Converts the keyword keys of a map to their string representations. This is useful for passing to the JDBI DAO
   for the purpose of using the maps to modify the database"
  [record]
  (into {}
        (for [[k v] record]
          [(name k) v])))

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
        (.insertOption (map-key->string-key option)))))

(def option (Option. nil
                     1
                     "this is an instance test"
                     nil
                     nil))
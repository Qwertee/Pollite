(ns com.jberrend.pollite.backend.db
  (:require [honeysql.core :as sql])
  (:import (org.jdbi.v3.core Jdbi)
           (org.jdbi.v3.sqlobject SqlObjectPlugin)
           (com.jberrend.pollite.backend.models.option Option)
           (com.jberrend.pollite.backend.models.poll Poll))
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

   ex. (select OptionMapper {:select [:*] :from [:option]}"
  [mapper query]
  `(let [handle# (.open ds)]
     (-> handle#
         ; (first (sql/format ~query))
         (.createQuery ~query)
         (.map (new ~mapper)))))

(defmacro insert
  "Inserts the provided model instance into the given database using the given DAO for translation."
  ;; TODO: get rid of dao-type - infer based on type of model
  [model dao-type]
  `(let [handle# (.open ds)
         dao# (.attach handle# ~dao-type)]
     (-> dao#
         (.insert (map-key->string-key ~model)))))

;; example option instance for repl use
(def option (Option. nil
                     1
                     "this is an instance test"
                     nil
                     nil))

;; example poll instance for repl use
(def poll (Poll. nil
                 "Is this a good prompt?"
                 "0xHASHCODE"
                 nil))
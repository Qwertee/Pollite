(ns com.jberrend.pollite.backend.db
  (:require [honeysql.core :as sql])
  (:import (org.jdbi.v3.core Jdbi)
           (org.jdbi.v3.sqlobject SqlObjectPlugin))
  (:gen-class))

;; taken (and slightly modified) from https://stackoverflow.com/a/7781443
(defn- load-props
  [file-name]
  (with-open [^java.io.Reader reader (-> file-name
                                         clojure.java.io/resource
                                         .getFile
                                         clojure.java.io/reader)]
    (let [props (java.util.Properties.)]
      (.load props reader)
      (into {} (for [[k v] props] [(keyword k) (read-string v)])))))

(def properties (load-props "props.properties"))

(def ds (Jdbi/create (:db-conn-str properties) (:db-username properties) (:db-password properties)))

(defn initialize
  "Initializes the necessary plugins for JDBI before any transactions can take place"
  []
  (.installPlugin ds (SqlObjectPlugin.)))

(defn map-key->string-key
  "Converts the keyword keys of a map to their string representations. This is useful for
   passing to the JDBI DAO for the purpose of using the maps to modify the database"
  [record]
  (into {}
        (for [[k v] record]
          [(name k) v])))

(defmacro select
  "Executes the given query map, placing the matching rows into
   objects as determined by the passed mapper type.

   Returns a VECTOR of all found records (even if none or only one).

   ex. (select OptionMapper {:select [:*] :from [:option]}"
  [mapper query]
  `(let [handle# (.open ds)]
     (-> handle#
         ; (first (sql/format ~query))
         (.createQuery ~query)
         (.map (new ~mapper))
         (.list))))

(defmacro select-only
  "Executes a select statement, but ensures that only one result is returned.
   Throws an exception if zero, or more than one result was returned from query."
  [mapper query]
  `(let [res# (select ~mapper ~query)]
     (if (= 1 (count res#))
       (first res#)
       (throw (Exception. (str "Not one element was returned by query: " ~query))))))

(defmacro insert
  "Inserts the provided model instance into the given database using the given DAO for
   translation."
  ;; TODO: get rid of dao-type - infer based on type of model
  [model dao-type]
  `(let [handle# (.open ds)
         dao# (.attach handle# ~dao-type)]
     (-> dao#
         (.insert (map-key->string-key ~model)))))

;; example option instance for repl use
;(def option (Option. nil
;                     1
;                     "this is an instance test"
;                     nil
;                     nil))
;
;;; example poll instance for repl use
;(def poll (Poll. nil
;                 "Is this a good prompt?"
;                 "0xHASHCODE"
;                 nil))

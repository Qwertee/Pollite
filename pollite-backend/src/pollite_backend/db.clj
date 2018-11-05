(ns pollite-backend.db
  (:require [honeysql.core :as sql])
  (:import (org.jdbi.v3.core Jdbi)
           (pollite_backend.models.option OptionMapper)))

(def ds (Jdbi/create "jdbc:mysql://167.99.235.49:3306/pollite_test" "pollite_user" "password"))

(defmacro select
  "executes the given query map, placing the matching rows into
   objects as determined by the passed mapper type

   ex. (select OptionMapper 'select * from option'"
  [mapper query]
  `(let [handle# (.open ds)]
     (-> handle#
         (.createQuery (first (sql/format ~query)))
         (.map (new ~mapper)))))

(defn get-options []
  (select OptionMapper "select * from option"))
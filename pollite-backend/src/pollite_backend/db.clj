(ns pollite-backend.db
  (:import (org.jdbi.v3.core Jdbi)
           (pollite_backend.models.option OptionMapper)))

(def ds (Jdbi/create "jdbc:mysql://167.99.235.49:3306/pollite_test" "pollite_user" "password"))

(defn foo []
  (let [handle (.open ds)]
    (.mapTo (.createQuery handle "select text from option limit 1") String)))

(defn get-options []
  (let [handle (.open ds)]
    (-> handle
        (.createQuery "select * from option")
        (.map (OptionMapper.))
        ;(.list) ; having this seems to result in a loss of data
        )))

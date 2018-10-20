(in-package :cl-user)
(defpackage pollite-backend
  (:use :cl))
(in-package :pollite-backend)

;; blah blah blah.

(defun connect (password)
  (mito:connect-toplevel :mysql
                         :host "167.99.235.49"
                         :database-name "pollite_test"
                         :username "pollite_user"
                         :password password))

(defun create-tables-if-needed ()
  (mapcar #'mito:ensure-table-exists '(poll voter)))

(defclass poll ()
  ((id :col-type :int
       :initarg :id
       :reader id
       :primary-key t)
   (prompt :col-type :text
           :initarg prompt
           :accessor prompt)
   (hash :col-type :text
         :initarg hash
         :accessor hash))
  (:metaclass mito:dao-table-class))

(defclass voter ()
  ((id :col-type :int
       :initarg :id
       :reader id
       :primary-key t)
   (poll_id :col-type :int
            :initarg poll-id
            :accessor poll-id)
   (hash :col-type :text
         :initarg hash
         :accessor hash))
  (:metaclass mito:dao-table-class))

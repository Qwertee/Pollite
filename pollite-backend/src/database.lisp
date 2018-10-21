(in-package :cl-user)
(defpackage pollite-backend
  (:use :cl))
(in-package :pollite-backend)

;; blah blah blah.
(defparameter *tables* '(poll vote option))

(defun connect (password)
  (mito:connect-toplevel :mysql
                         :host "167.99.235.49"
                         :database-name "pollite_test"
                         :username "pollite_user"
                         :password password))

(defun create-tables-if-needed ()
  (mapcar #'mito:ensure-table-exists *tables*))

(defun migrate-all-tables ()
  (mapcar #'mito:migrate-table *tables*))

(defclass poll ()
  ((id :col-type :int
       :initarg :id
       :reader id
       :primary-key t)
   (prompt :col-type :text
           :initarg :prompt
           :accessor prompt)
   (hash :col-type :text
         :initarg :hash
         :accessor hash))
  (:metaclass mito:dao-table-class))

(defclass vote ()
  ((id :col-type :int
       :initarg :id
       :reader id
       :primary-key t)
   ;; references which poll the voter has voted on
   (option_id :col-type :int
            :initarg option-id
            :accessor option-id)
   (hash :col-type :text
         :initarg :hash
         :accessor hash)
   (address :col-type :text
            :initarg :address
            :accessor address))
  (:metaclass mito:dao-table-class))

(defclass option ()
  ((id :col-type :int
       :initarg :id
       :reader id
       :primary-key t)
   ;; references which poll the voter has voted on
   (poll_id :col-type :int
            :initarg :poll-id
            :accessor poll-id)
   (text :col-type :text
         :initarg :text
         :reader text))
  (:metaclass mito:dao-table-class))

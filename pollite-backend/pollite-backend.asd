#|
  This file is a part of pollite-backend project.
|#

(in-package :cl-user)
(defpackage pollite-backend-asd
  (:use :cl :asdf))
(in-package :pollite-backend-asd)

(defsystem pollite-backend
  :version "0.1"
  :author ""
  :license ""
  :depends-on ("mito"
               "caveman")

  :components ((:module "src"
                :components
                ((:file "database"))))
  :description ""
  :long-description
  #.(with-open-file (stream (merge-pathnames
                             #p"README.markdown"
                             (or *load-pathname* *compile-file-pathname*))
                            :if-does-not-exist nil
                            :direction :input)
      (when stream
        (let ((seq (make-array (file-length stream)
                               :element-type 'character
                               :fill-pointer t)))
          (setf (fill-pointer seq) (read-sequence seq stream))
          seq))))

(ns crud.service
  (:require [crud.dao :as dao]
            [crud.image-helper :as helper]))

(defn save [params] (dao/user-create params))
(defn login [user pass] (let [result (dao/user-login user pass)] (if (or (= nil result) (= nil :username result)) (throw (Exception. "No user found")) result)))
; TODO change user
(defn upload-file [file] (dao/image-create {:filename (get file :filename) :value (helper/image->byte-array (helper/scale-image (get file :tempfile) 100)) :contenttype (get file :content-type) :userid 1}))
(defn show-all [] (map (fn [x] (helper/getBase64 (x :value))) (dao/get-all-images 1)))
(defn get-image [] (helper/getBase64 ((dao/get-image 1) :value)))



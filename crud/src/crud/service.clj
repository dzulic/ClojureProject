(ns crud.service
  (:require [crud.dao :as dao]
            [crud.helper :as helper]
            [mikera.image.core :as mikera])
  )
(defn- get-image-details [x]
  (into
    {:val  (helper/getBase64 (x :value))
     :id   (x :id)
     :name (x :filename)}))

(defn save [params] (dao/user-create params))
(defn login [user pass] (let [result (dao/user-login user pass)]
                          (if (or (= nil result) (= nil :username result))
                            (throw (Exception. "No user found"))
                            result)))
; TODO change user
(defn upload-file [file name]
  (dao/image-create
    {:filename    name
     :value       (helper/image->byte-array (helper/scale-image (get file :tempfile) 100))
     :contenttype (get file :content-type)
     :userid      1}))

(defn show-all [] (map get-image-details (dao/get-all-images 1)))
(defn get-image [id]
  (get-image-details (dao/get-image id)))

(defn get-pixels [id]
  (println id)
  (mikera/get-pixels
    ((dao/get-image id) :value)
    ))
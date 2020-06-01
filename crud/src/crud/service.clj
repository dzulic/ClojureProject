(ns crud.service
  (:require [crud.dao :as dao]
            [crud.helper :as helper]
            [mikera.image.core :as mikera])
  )

(defn save [params] (dao/user-create params))
(defn login [user pass] (let [result (dao/user-login user pass)] (if (or (= nil result) (= nil :username result)) (throw (Exception. "No user found")) result)))
; TODO change user
(defn upload-file [file name]
  (dao/image-create {:filename name :value (helper/image->byte-array (helper/scale-image (get file :tempfile) 100)) :contenttype (get file :content-type) :userid 1}))
(defn show-all [] (map (fn [x] (into {:val (helper/getBase64 (x :value))} {:id (x :id)})) (dao/get-all-images 1)))
(defn get-image [id] (let [image (helper/getBase64 ((dao/get-image id) :value))] image))
(defn get-pixels [image]
  (println image)
  (println (let [pxs (mikera/get-pixels (helper/bufferedImage image))] pxs)))
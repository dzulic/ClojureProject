(ns crud.service
  (:require [crud.dao :as dao]))

(defn save [params] (dao/user-create params))

(defn login [params] (dao/user-login params))

(defn upload-file [file] (dao/image-create {:filename (get file :filename) :value (byte-array (.length (get file :tempfile))) :contenttype (get file :content-type) :userid 1}))

(ns crud.service
  (:require [crud.dao :as dao]
            [clojure.java [io :as io]])
  )

(defn save [params] (dao/user-create params))

(defn login [params] (dao/user-login params))

(defn file-to-byte-array
  [^java.io.File file]
  (let [result (byte-array (.length file))]
    (with-open [in (java.io.DataInputStream. (clojure.java.io/input-stream file))]
      (.readFully in result))
    result))

(defn upload-file [file]
  (println file)
  (println (file-to-byte-array (get file :tempfile)))
  )


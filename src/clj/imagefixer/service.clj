(ns imagefixer.service
  (:require [imagefixer.dao :as dao]
            [imagefixer.helper :as helper])
  )

(defn- get-image-details [x]
  (into
    {:val  (helper/getBase64 (x :value))
     :id   (x :id)
     :name (x :filename)}))

(defn create-user [params] (dao/user-create params))
(defn login [user pass] (let [result (dao/user-login user pass)]
                          (if (or (= nil result) (= nil :username result))
                            false
                            true)))
(defn upload-file [file name]
  (dao/image-create
    {:filename    name
     :value       (helper/image->byte-array (get file :tempfile))
     :contenttype (get file :content-type)
     :userid      1}))

(defn show-all [] (map get-image-details (dao/get-all-images 1)))
(defn get-image [id]
  (get-image-details (dao/get-image id)))

(defn average-array [a]
  (int (Math/ceil (/ (reduce + a) (count a))))
  )

(defn get-pixels [pimage]
  (let [array
        (for [x (range (count (.pixels pimage)))]
          (let [p (helper/convertHexToRGB (clojure.string/replace (aget (.pixels pimage) x) #"-" ""))]
            (aset (.pixels pimage) x (average-array p))
            )
          )]
    array))

(defn fix-the-image [id]
  (let [pimage (helper/get-p-image (dao/get-image id))
        height (.width pimage)
        width (.height pimage)]
    (.loadPixels pimage)
    (.updatePixels pimage)
    (.loadPixels pimage)
    (helper/getBase64 (helper/bfimage->byte-array (.getNative pimage)))))
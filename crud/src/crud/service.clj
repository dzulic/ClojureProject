(ns crud.service
  (:require [crud.dao :as dao]
            [crud.helper :as helper])
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
(defn upload-file [file name]
  (dao/image-create
    {:filename    name
     :value       (helper/image->byte-array (get file :tempfile))
     :contenttype (get file :content-type)
     :userid      1}))

(defn show-all [] (map get-image-details (dao/get-all-images 1)))
(defn get-image [id]
  (get-image-details (dao/get-image id)))

(defn get-pixels [id]
  (let [pimage (helper/get-p-image (dao/get-image id))]
    (.loadPixels pimage)
    (let [array
          (for [x (range (count (.pixels pimage)))]
            (helper/convertHexToRGB (clojure.string/replace (aget (.pixels pimage) x) #"-" "")))]
      array))
  )

(defn convert-to-black-and-white [id]
  (let [pimage (helper/get-p-image (dao/get-image id))
        height (.width pimage)
        width (.height pimage)]
    (.loadPixels pimage)
    (let [array
          (for [x (range (count (.pixels pimage)))]
            (helper/convertHexToRGB (clojure.string/replace (aget (.pixels pimage) x) #"-" "")))]
      (for [x (range (* height width))]
        ((clojure.string/replace (aget (.pixels pimage) x) #"-" "")
         (aset (.pixels pimage) x (get array x)))))

    (.updatePixels pimage)
    (.getImage pimage)))
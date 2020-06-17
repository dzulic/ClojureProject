(ns crud.helper
  (:import (javax.imageio ImageIO)
           (java.io ByteArrayOutputStream FileInputStream ByteArrayInputStream)
           (javax.imageio ImageIO)
           (java.util Base64)
           (java.awt Color)
           (processing.core PImage))
  (:require [clojure.java.io :as io]))

(defn bfimage [array]
  (let [img (ByteArrayInputStream. array)]
    (ImageIO/read img)))

(defn bfimage->byte-array [image]
  (let [baos (ByteArrayOutputStream.)]
    (ImageIO/write image "jpg" baos)
    (.toByteArray baos)))

(defn image->byte-array [image]
  (let [baos (ByteArrayOutputStream.)]
    (ImageIO/write (ImageIO/read image) "jpg" baos)
    (.toByteArray baos)))

(defn getBase64 [img-bytes] (.encodeToString (Base64/getEncoder) img-bytes))

(defn convertHexToRGB [val]
  (let [hexInt (Integer/parseInt val)]
    (let [r (bit-shift-right (bit-and hexInt 0xFF0000) 16)
          g (bit-shift-right (bit-and hexInt 0xFF00) 8)
          b (bit-and hexInt 0xFF)]
      (into [r g b])
      )))

(defn hex->rgb [[_ & rgb]]
  (map #(->> % (apply str "0x") (Long/decode))
       (partition 2 rgb)))

(defn get-p-image [img]
  (PImage. (bfimage (get img :value))))

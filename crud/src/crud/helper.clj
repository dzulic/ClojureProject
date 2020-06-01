(ns crud.helper
  (:import (javax.imageio ImageIO)
           (java.io ByteArrayOutputStream FileInputStream)
           (java.awt.geom AffineTransform)
           (java.awt.image AffineTransformOp BufferedImage)
           (javax.imageio ImageIO)
           (java.util Base64)))

(defn scale [img ratio width height]
  (let [scale (AffineTransform/getScaleInstance
                (double ratio) (double ratio))
        transform-op (AffineTransformOp.
                       scale AffineTransformOp/TYPE_BILINEAR)]
    (.filter transform-op img (BufferedImage. width height (.getType img)))))

(defn scale-image [file thumb-size]
  (let [img (ImageIO/read file)
        img-width (.getWidth img)
        img-height (.getHeight img)
        ratio (/ thumb-size img-height)]
    (scale img ratio (int (* img-width ratio)) thumb-size)))

(defn image->byte-array [image]
  (let [baos (ByteArrayOutputStream.)]
    (ImageIO/write image "jpeg" baos)
    (.toByteArray baos)))

(defn getBase64 [img-bytes] (.encodeToString (Base64/getEncoder) img-bytes))
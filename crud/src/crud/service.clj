(ns crud.service
  (:require [crud.dao :as dao]))

(defn save [params] (dao/user-create params))

(defn login [params] (dao/user-login params))


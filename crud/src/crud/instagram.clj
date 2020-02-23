(ns crud.instagram
  (:require [clj-instagram.oauth :as oauth]
            [clj-instagram.endpoints :as endpoints]))

(def client-id "1467442286766587")
(def client-secret "2be1cb85b183bd0021acc258feba800f")
(def redirect-uri "http://localhost:8083/oauth/callback/")

(oauth/authorization-url client-id redirect-uri)

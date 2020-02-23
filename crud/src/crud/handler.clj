(ns crud.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as resp]
            [ring.middleware.basic-authentication :refer :all]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [crud.views :as views]
            [crud.service :as service]
            )
  (:gen-class))


(defn authenticated? [name pass]
  (and (= name "admin")
       (= pass "admin")))

(defroutes public-routes
           (GET  "/" [] (views/main-page))
           (route/resources "/"))

(defroutes protected-routes
           (POST "/instagram/login" [] (views/login))
           (POST "/instagram/analyse" [] (views/analyse))
           (POST "/instagram/save" [& params]
                 (do (service/save (:id params) params)
                     (resp/redirect "views/success"))))


(defroutes app-routes
           public-routes
           (wrap-basic-authentication protected-routes authenticated?)
           (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           8083))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))
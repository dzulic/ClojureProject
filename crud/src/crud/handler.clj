(ns crud.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [ring.middleware.basic-authentication :refer :all]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [crud.views :as views]
            [crud.service :as service]
            [ring.util.response :as response])
  (:gen-class))

(defroutes protected-routes
           (GET "/asas" [] "as"))

(defroutes public-routes
           (GET "/main" [] (views/main-page))
           (GET "/create" [] (views/create-user))
           (POST "/save" [& params]
             (do (service/save params)))
           (POST "/do-login" [& params]
                                     (do (service/login params)))
           (GET "/login" [] (views/login)))

(defroutes home-routes
           (GET "/success" [] (views/success))
           (GET "/callback" [] (views/callback))
           (route/resources "/"))


(defroutes app-routes
           public-routes
           home-routes
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
                            :join? false}))
  )
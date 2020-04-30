(ns crud.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :refer :all]
            [crud.views :as views]
            [crud.service :as service]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.session :refer :all]
            [ring.middleware.http-basic-auth :refer [wrap-with-auth wrap-require-auth]]
            )
  (:gen-class))

(defn authenticate [name pass]
  (if (and (= nil? (get :session :username))
           (= name (get :session :username)))
    true
    false))
(defn authenticated [a b] true)

(defroutes protected-routes
           (GET "/asas" [] "as"))

(defroutes public-routes
           (GET "/main" [] (views/main-page))
           (GET "/create" [] (views/create-user))
           (POST "/save" [& params]
             (do (service/save params)
                 (redirect (views/login))))
           (POST "/do-login" [params]
             (do (service/login (get params :username) (get params :password))
                 (assoc (redirect (views/success)) :session {:username (get params "username")})))
           (GET "/logout" []
             (assoc (redirect "/") :session nil))
           (GET "/login" [] (views/login))
           (POST "/upload-file" {{und :params} :arguments :as arguments}
             (service/upload-file (val (first (get arguments :params))))))
(defroutes home-routes
           (GET "/success" [] (views/success))
           (route/resources "/"))


(defroutes app-routes
           (wrap-with-auth public-routes authenticated)
           (wrap-with-auth home-routes authenticated)
           (wrap-require-auth protected-routes authenticate "The Secret Area" {:body "You're not allowed in The Secret Area!"})
           (route/not-found "Not Found"))


(def app
  (->
    app-routes
    wrap-multipart-params
    wrap-params
   ))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           8083))]
    (jetty/run-jetty #'app {:port  port
                            :join? false}))
  )
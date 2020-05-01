(ns crud.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes context]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :refer :all]
            [ring.middleware.basic-authentication :refer [wrap-basic-authentication]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [crud.views :as views]
            [crud.service :as service]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.session :as session]
            )
  (:gen-class))

(defn authenticated? [name pass]
  (println (get :session :username))
  (if (and (= nil? (get :session :username))
           (= name (get :session :username)))
    true
    false))

(defroutes protected-routes
           (context "/documents" [] :tags ["api-documents"]
                                    (GET "/asas" [] "as")))

(defroutes public-routes
           (GET "/main" [] (views/main-page))
           (GET "/create" [] (views/create-user))
           (POST "/save" [& params]
             (do (service/save params)
                 (redirect (views/login))))
           (POST "/do-login" [& params]
             (do (service/login (get params :username) (get params :password))
                 (assoc (redirect "/main") :session {:username (get params :username)})))
           (GET "/logout" [req]
             (assoc (redirect "/") :session nil))
           (GET "/login" [] (views/login))
           (POST "/upload-file" {{und :params} :arguments :as arguments}
             (service/upload-file (val (first (get arguments :params)))))
           (GET "/gallery" [] (views/gallery (service/show-all)))
           (GET "/get" [] (views/analyse (service/get-image)))

           )
(defroutes home-routes
           (GET "/success" [] (views/success))
           (route/resources "/"))


(defroutes app-routes
           public-routes
           home-routes
           protected-routes
           (route/not-found "Not Found"))


(def app
  (-> app-routes
      wrap-multipart-params
      wrap-params
      (wrap-defaults api-defaults)
      (session/wrap-session)))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           8083))]
    (jetty/run-jetty #'app {:port  port
                            :join? false}))
  )
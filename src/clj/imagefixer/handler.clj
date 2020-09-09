(ns imagefixer.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes context]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :refer :all]
            [ring.middleware.basic-authentication :refer [wrap-basic-authentication]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [imagefixer.views :as views]
            [imagefixer.service :as service]
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
           (GET "/" [] (views/main))
           (GET "/upload" [] (views/upload-file))
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
           (POST "/upload-file" {{par :params} :arguments :as arguments}
             (service/upload-file (val (first (get arguments :params))) (val (second (get arguments :params)))))
           (GET "/gallery" [] (views/gallery (service/show-all)))
           (GET "/get" [& params]
             (views/fixer (service/get-image (get params :id))))
           (POST "/getpixels" [& params] (views/view-image (service/fix-the-image (get params "id")))))

(defroutes home-routes
           (route/resources "/"))


(defroutes app-routes
           public-routes
           home-routes
           (route/not-found "Not Found"))


(def app
  (-> app-routes
      wrap-multipart-params
      wrap-params
      (wrap-defaults api-defaults)
      (session/wrap-session)))


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
            [buddy.auth.accessrules :refer [restrict]]
            )
  (:gen-class))

(defn authenticated? [{session :session :as req}]
  (println "SESSION ")
  (not (nil? session)))

(defn login-user [{params  :form-params
                   session :session :as req}]
  (do (if (service/login (get params "username") (get params "password"))
        (assoc (redirect "/session/main") :session (assoc session :identity (get params "username")))
        (redirect "/login"))))

(defn create-user [{params :form-params :as req}]
  (do (service/create-user params)
      (redirect "/login")))

(defn get-image [{params  :params
                  session :session :as req}]
  (println "PARAMS")
  (println req)
  (views/fixer (service/get-image (get params :id))))

(defn logout-user [{session :session :as req}]
  (assoc (redirect "/") :session (dissoc session :identity)))

(defroutes public-routes
           (GET "/" [] (views/welcome))
           (POST "/create-user" req (create-user req))
           (POST "/do-login" req (login-user req))
           (GET "/login" [] (views/login))
           (GET "/create" [] (views/create-user)))

(defroutes home-routes
           (route/resources "/")
           (route/resources "/session/"))

(defroutes protected-routes
           (GET "/main" [] (views/home))
           (GET "/upload" [] (views/upload-file))
           (GET "/logout" req (logout-user req))
           (POST "/upload-file" {{par :params} :arguments :as arguments}
             (service/upload-file (val (first (get arguments :params))) (val (second (get arguments :params))))
             (redirect "gallery"))
           (GET "/gallery" [] (views/gallery (service/show-all)))
           (GET "/get" req (get-image req))
           (POST "/getpixels" [& params] (views/view-image (service/fix-the-image (get params "id"))))
           )

(defroutes app-routes
           public-routes
           home-routes
           (context "/session" [] (restrict protected-routes {:handler authenticated?}))
           (route/not-found "Not Found"))
(def app
  (-> app-routes
      wrap-multipart-params
      wrap-params
      (wrap-defaults api-defaults)
      (session/wrap-session)))


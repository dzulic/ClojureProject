(ns crud.views
  (:use [hiccup.page :refer :all])
  (:require [hiccup.core :refer (html)]
            [hiccup.form :as f]
            ))

(defn layout [title & content]
  (html
    [:head [:meta {:charset "utf-8"}] [:meta {:http-equiv "X-UA-Compatible", :content "IE=edge"}]
     [:link {:rel "stylesheet" :type "text/css" :href "main.css"}]
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:name "description", :content ""}] [:meta {:name "author", :content "Julija"}]
     [:title title]
     ]
    [:body
     [:div {:class "header"}]
     [:div {:class "container"} content]
     [:div {:class "footer"}]
     ])
  )

(defn main-page []
  (layout "Analyser"))


(defn create-user []
  (layout "Create User"
          [:div {:class "row"}
           [:div {:class "col-lg-6"}
            (f/form-to {:role "form"} [:post "/save"]
                       [:h2 "Let's create your user"]
                       (f/label {} "title" "Name") [:br]
                       (f/text-field {:class "form-control"} "name") [:br]
                       (f/label {} "title" "Date of birth") [:br]
                       (f/text-field {:class "form-control"} "dateOfBirth") [:br]
                       (f/label {} "title" "Username") [:br]
                       (f/text-field {:class "form-control"} "username") [:br]
                       (f/label {} "body" "Password") [:br]
                       (f/password-field {:class "form-control"} "password") [:br]
                       (f/label {} "body" "Email") [:br]
                       (f/text-field {:class "form-control"} "email") [:br]
                       (f/submit-button {:class "btn btn-primary"} "Submit"))]])
  )

(defn login []
  (layout "Login"
          [:div {:class "row"}
           [:div {:class "col-lg-6"}
            (f/form-to {:role "form"} [:post "/do-login"]
                       [:h2 "Please login with your credentials"]
                       (f/label {} "title" "Username") [:br]
                       (f/text-field {:class "form-control"} "username") [:br]
                       (f/label {} "body" "Password") [:br]
                       (f/password-field {:class "form-control"} "password") [:br]
                       (f/submit-button {:class "btn btn-primary"} "Submit"))]]))
(def analyse)
(def upload)

(defn success []
  (layout "SUCCESS"
          [:div {:class "row"}
           [:div {:class "col-lg-6"}
            [:p "WOOOOHOOOOOO!"]
            [:p "It is a success!"]
            ]]))

(defn callback []
  (layout "Callback"
          [:div {:class "row"}
           [:div {:class "col-lg-6"}
            [:p "CALLL!"]
            ]]))




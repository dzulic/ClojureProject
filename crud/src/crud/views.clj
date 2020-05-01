(ns crud.views
  (:use [hiccup.page :refer :all])
  (:require [hiccup.core :refer (html)]
            [hiccup.form :as f]
            ))

(defn layout [title & content]
  (html
    [:head
     [:link {:rel "icon" :href "favicon.ico" :type "image/x-icon" :sizes "16x16"}]
     [:link {:rel "stylesheet" :href "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" :integrity "sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" :crossorigin "anonymous"}]
     [:meta {:charset "utf-8"}] [:meta {:http-equiv "X-UA-Compatible", :content "IE=edge"}]
     [:link {:rel "stylesheet" :type "text/css" :href "main.css"}]
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:name "description", :content ""}] [:meta {:name "author", :content "Julija"}]
     [:title title]
     ]
    [:body
     [:div {:class "header"}
      [:img {:src "favicon.ico" :class "logo"}]
      [:h1 "Image Fixer"]]
     [:div {:class "container"} content]
     [:div {:class "footer"}]
     ])
  )

(defn main-page []
  (layout "Fixer"
          [:div {:class "row"}
           [:div {:class "col-lg-8 offset-lg-2"}
            (f/form-to {:role "form" :enctype "multipart/form-data"} [:post "/upload-file"]
                       (f/file-upload {:class "form-control"} "file") [:br]
                       (f/submit-button {:class "btn btn-primary"} "Submit")
                       )]]))


(defn create-user []
  (layout "Create User"
          [:div {:class "row"}
           [:div {:class "col-lg-8 offset-lg-2"}
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
           [:div {:class "col-lg-8 offset-lg-2"}
            (f/form-to {:role "form"} [:post "/do-login"]
                       [:h2 "Please login with your credentials"]
                       (f/label {} "title" "Username") [:br]
                       (f/text-field {:class "form-control"} "username") [:br]
                       (f/label {} "body" "Password") [:br]
                       (f/password-field {:class "form-control"} "password") [:br]
                       (f/submit-button {:class "btn btn-primary"} "Submit"))]]))

(defn analyse [img]
  (layout "Image"
          [:div {:class "row"}
           [:div {:class "col-lg-8 offset-lg-2"}
            [:div {:class "row"}
             [:img {:src (str "data:image/png;base64," img)}]]
            [:div {:class "row"}
             [:button {:type "button" :class "back" :onclick "window.location.href='/gallery'"} "Go back to gallery"]]
            ]]))

(defn gallery [array]
  (layout "Gallery"
          [:div {:class "row"}
           [:div {:class "col-lg-8 offset-lg-2"}
            [:h2 "Here is your gallery"]
            [:ul
             (for [x array]
               [:li [:img {:src (str "data:image/png;base64," x)}]])]
            ]]))




(defn success []
  (layout "SUCCESS"
          [:div {:class "row"}
           [:div {:class "col-lg-8 offset-lg-2"}
            [:p "WOOOOHOOOOOO!"]
            [:p "It is a success!"]
            ]]))


(ns imagefixer.views
  (:use [hiccup.page :refer :all])
  (:require [hiccup.core :refer (html)]
            [hiccup.form :as f]
            [hiccup.element :require image]
            ))

(defn layout [title content navigation]
  (html
    [:head
     [:link {:rel "icon" :href "favicon.ico" :type "image/x-icon" :sizes "16x16"}]
     [:link {:rel "stylesheet" :href "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" :integrity "sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" :crossorigin "anonymous"}]
     [:meta {:charset "utf-8"}] [:meta {:http-equiv "X-UA-Compatible", :content "IE=edge"}]
     [:link {:rel "stylesheet" :type "text/css" :href "./main.css"}]
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:name "description", :content ""}] [:meta {:name "author", :content "Julija"}]
     [:title title]]
    [:body
     [:div {:class "header"}
      [:img {:src "./favicon.ico" :class "logo"}]
      [:h1 "Image Fixer"]
      [:div {:class "navigation"} navigation]]
     [:div {:class "container"} content]
     [:div {:class "footer"}]
     ])
  )

(defn layout-without-session [title & content]
  (layout title content [:div {:class "navigation"}
                         [:a {:href "/"} "Welcome"]
                         [:a {:href "/login"} "Login"]
                         [:a {:href "/create"} "Create user"]
                         ]))

(defn layout-with-session [title & content]
  (layout title content [:div {:class "navigation"}
                         [:a {:href "/session/main"} "Home"]
                         [:a {:href "/session/upload"} "Upload to gallery"]
                         [:a {:href "/session/gallery"} "Gallery"]
                         [:a {:href "/session/logout"} "Logout"]]))


(defn upload-file []
  (layout-with-session "Upload File"
                       [:div {:class "row"}
                        [:div {:class "col-lg-8 offset-lg-2"}
                         (f/form-to {:role "form" :enctype "multipart/form-data"} [:post "/session/upload-file"]
                                    (f/label {} "pleaseUpload" "Please upload only PNG format")
                                    (f/file-upload {:class "form-control"} "file") [:br]
                                    (f/label {} "name" "Image Name") [:br]
                                    (f/text-field {:class "form-control"} "name") [:br]
                                    [:div {:class "row"}
                                     (f/submit-button {:class "btn btn-primary btn-center"} "Save image")]
                                    [:div {:class "row"}
                                     [:button {:type "button" :class "btn btn-primary btn-center" :onclick "window.location.href='/session/gallery'"} "Go to gallery"]]
                                    )]]))


(defn create-user []
  (layout-without-session "Create User"
                          [:div {:class "row"}
                           [:div {:class "col-lg-8 offset-lg-2"}
                            (f/form-to {:role "form"} [:post "/create-user"]
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
  (layout-without-session "Login"
                          [:div {:class "row"}
                           [:div {:class "col-lg-8 offset-lg-2"}
                            (f/form-to {:role "form"} [:post "/do-login"]
                                       [:h2 "Please login with your credentials"]
                                       (f/label {} "title" "Username") [:br]
                                       (f/text-field {:class "form-control"} "username") [:br]
                                       (f/label {} "body" "Password") [:br]
                                       (f/password-field {:class "form-control"} "password") [:br]
                                       (f/submit-button {:class "btn btn-primary"} "Submit"))]]))

(defn fixer [img]
  (layout-with-session "Image Fixer"
                       [:div {:class "row"}
                        [:div {:class "col-lg-8 offset-lg-2"}
                         (f/form-to {:role "form" :enctype "multipart/form-data"} [:post "/session/getpixels"]
                                    [:div {:class "row"}
                                     [:img {:src (str "data:image/png;base64," (get img :val)) :id "big-image"}]]
                                    (f/label {} "body" (get img :name)) [:br]
                                    (f/hidden-field "image" (get img :val))
                                    (f/hidden-field "id" (get img :id))
                                    [:div {:class "row"}
                                     [:button {:type "button" :class "btn btn-primary" :onclick "window.location.href='/session/gallery'"} "Go back to gallery"]
                                     (f/submit-button {:class "btn btn-primary"} "Manipulate the image")]
                                    )]]))

(defn view-image [img]
  (layout-with-session "Image Fixer"
                       [:div {:class "row"}
                        [:div {:class "col-lg-8 offset-lg-2"}
                         (f/form-to {:role "form" :enctype "multipart/form-data"} [:post "/session/getpixels"]
                                    [:div {:class "row"}
                                     [:img {:src (str "data:image/png;base64," img) :id "big-image"}]]
                                    [:div {:class "row"}
                                     [:button {:type "button" :class "btn btn-primary" :onclick "window.location.href='/session/gallery'"} "Go back to gallery"]
                                     ]
                                    )]]))

(defn gallery [array]
  (layout-with-session "Gallery"
                       [:div {:class "row"}
                        [:div {:class "col-lg-8 offset-lg-2"}
                         [:h2 "Here is your gallery"]
                         [:h3 "Select image to edit"]
                         (f/form-to {:role "form" :enctype "multipart/form-data"} [:get "/session/get"]
                                    [:ul (for [x array]
                                           [:li
                                            [:input {:type :radio :value (get x :id) :name "id"}]
                                            [:img {:src (str "data:image/png;base64," (get x :val)) :class "gallery"}]])
                                     ]
                                    (f/submit-button {:class "btn btn-primary"} "Edit image"))]]))
(defn welcome []
  (layout-without-session "Welcome"
                          [:div {:class "row"}
                           [:div {:class "col-lg-8 offset-lg-2"}
                            [:h2 "Welcome"]
                            ]]))
(defn home []
  (layout-with-session "Home"
                       [:div {:class "row"}
                        [:div {:class "col-lg-8 offset-lg-2"}
                         [:h2 "Welcome"]
                         ]]))
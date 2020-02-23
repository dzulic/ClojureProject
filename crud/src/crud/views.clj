(ns crud.views
  (:require [hiccup.core :refer (html)]
            [hiccup.form :as form]
            ))

(defn layout [title & content]
  (html
    [:head [:meta {:charset "utf-8"}] [:meta {:http-equiv "X-UA-Compatible", :content "IE=edge"}]
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:name "description", :content ""}] [:meta {:name "author", :content "Julija"}]
     [:title title]
     ]
    [:body content
     [:div {:class "container"}
      [:hr {}] [:footer {} [:p {}]]]
     ])
  )

(defn main-page []
  (layout "Insta analyser"
          [:div {:class "container"}
           ]))

(def login)

(def analyse)

(def success)
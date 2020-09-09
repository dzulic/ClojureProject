(defproject clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]
                 [clj-postgresql "0.7.0"]
                 [cprop "0.1.14"]
                 [compojure "1.6.1"]
                 [luminus-jetty "0.1.7"]
                 [luminus-transit "0.1.1"]
                 [luminus/ring-ttl-session "0.3.3"]
                 [mount "0.1.16"]
                 [nrepl "0.6.0"]
                 [ring-basic-authentication "1.0.5"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.3.1"]
                 [ring-webjars "0.2.0"]
                 [ring-server "0.5.0"]
                 [quil/quil "2.3.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/tools.cli "0.4.2"]
                 [org.clojure/tools.logging "0.5.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.26"]
                 ]
  :main ^:skip-aot imagefixer.core
  :min-lein-version "2.0.0"
  :plugins [[lein-ring "0.12.5"]
            [compojure "1.1.6"]]
  :ring {:handler imagefixer.handler/app}
  :source-paths ["src/clj"]
  :target-path "target/%s/"
  :profiles
  {:uberjar {:omit-source  true
             :aot          :all
             :uberjar-name "imagefixer.jar"
             }
   :production
            {:ring
             {:open-browser? false, :stacktraces? false, :auto-reload? false}}

   :dev     {:dependencies [[javax.servlet/servlet-api "2.5"]
                            [ring/ring-mock "0.3.2"]
                            [figwheel "0.2.5"]
                            [com.cemerick/piggieback "0.2.0"]
                            [org.clojure/tools.nrepl "0.2.10"]
                            [weasel "0.6.0"]
                            ]}
   }

  )

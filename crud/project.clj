(defproject crud "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]
                 [clj-postgresql "0.7.0"]
                 [compojure "1.6.1"]
                 [ring-basic-authentication "1.0.5"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-defaults "0.1.5"]
                 [net.mikera/imagez "0.12.0"]
                 [hiccup "1.0.5"]]
  :main crud.handler
  :ring {:handler crud.handler/app}
  )

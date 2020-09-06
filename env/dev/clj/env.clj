(ns env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [imagefixerclojure.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[imagefixerclojure started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[imagefixerclojure has shut down successfully]=-"))
   :middleware wrap-dev})

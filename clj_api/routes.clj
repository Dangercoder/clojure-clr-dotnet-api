(ns clj-api.routes
  (:require [clj-api.interop.dotnet.app :refer [map-get map-post map-health-checks]]))

(defn configure-routes [app]
  (-> app
      (map-get "/" (fn [context]
                     (assoc context
                            :response/status 200
                            :response/body {"powered-by" "clojure-clr"})))
      (map-post "/json" (fn [context]
                          (let [person-name (-> context
                                                :request/raw-json-object
                                                (.get_Item "name")
                                                .ToString)]
                            (assoc context
                                   :response/body {"hello" person-name}
                                   :response/status 200))))
      (map-health-checks "/healthz")))
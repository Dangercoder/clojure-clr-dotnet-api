(ns clj-api.routes
  (:require [clj-api.interop.dotnet.app :refer [map-get map-post map-health-checks]]
            [clojure.spec.alpha :as s]))

(s/def ::username string?)
(s/def ::email string?)

(s/def ::create-user-request (s/keys :req-un [::username
                                              ::email]))

(defn handle-default-resp [ctx]
  (def ctx ctx)
  ctx
                               (assoc ctx
                                      :response/status (int 200)
                                      :response/body {"powered-by" "clojure-clr"}))

(defn configure-routes [app]
  (-> app
      (map-get "/" {:middeware []
                    :handler (fn [ctx]
                               (handle-default-resp ctx))})
      (map-post "/user" {:middleware []
                         :handler (fn [context]
                                    (let [person-name (-> context
                                                          :request/raw-json-object
                                                          (.get_Item "name")
                                                          .ToString)]
                                      (assoc context
                                             :response/body {"hello" person-name}
                                             :response/status 200)))})
      (map-health-checks "/healthz")))



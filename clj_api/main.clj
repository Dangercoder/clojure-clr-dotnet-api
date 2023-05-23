(ns clj-api.main
  (:require
   [clj-api.integrant.core :as integrant]
   [clj-api.routes :as routes]
   [clj-api.interop.dotnet.app :as app]
   [clj-api.interop.dotnet.builder :as builder])
  (:import
   [System.Threading CancellationTokenSource]
   [Microsoft.AspNetCore.Builder WebApplication]))

(def config {:component/web-app {::app/url "http://localhost:8080"}})

(defn create-app []
  (-> (WebApplication/CreateBuilder)
      (builder/add-health-checks [{:health-check/name "Database Health Check"
                                   :health-check/fn (fn [] {:health-check/status :healthy})}])
      builder/allow-synchronous-io
      .Build
      routes/configure-routes))

(defmethod integrant/init-key :component/web-app [_ opts]
  (println "Starting web app component")
  (-> (create-app)
      (app/run opts)))

(defmethod integrant/halt-key! :component/web-app [_ opts]
  (println "Stopping web app component")
  (let [token (-> (new CancellationTokenSource)
                  (.Token))]
   ;; TODO block this
    (.StopAsync (::app/app opts) token)))

(defn -main [& args]
;; TODO add proper shutdown hooks
  (integrant/init config))

(comment
  ;; start
  (def system (integrant/init config))

  ;; stop
  (integrant/halt! system)
  
  )
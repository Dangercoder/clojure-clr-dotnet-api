(ns clj-api.interop.dotnet.app
  (:require [clj-api.interop.dotnet.http :as http]
            [clojure.spec.alpha :as s])
  (:import [Microsoft.AspNetCore.Builder EndpointRouteBuilderExtensions HealthCheckEndpointRouteBuilderExtensions]
           [Microsoft.AspNetCore.Http HttpResponseJsonExtensions]
           [System.Threading.Tasks Task]
           [Microsoft.AspNetCore.Server.Kestrel.Core KestrelServerOptions]
           [Microsoft.AspNetCore.Hosting WebHostBuilderKestrelExtensions]
           [System.Threading CancellationTokenSource]
           [Microsoft.AspNetCore.Builder WebApplication WebApplicationBuilder EndpointRouteBuilderExtensions HealthCheckEndpointRouteBuilderExtensions]
           [Microsoft.AspNetCore.Http RequestDelegate HttpResponseWritingExtensions HttpContext]
           [Microsoft.Extensions.DependencyInjection HealthCheckServiceCollectionExtensions]))

(s/def ::url string?)
(s/def ::f future?)
(s/def ::app #(instance? Microsoft.AspNetCore.Builder.WebApplication %))

(defn run [^WebApplication app config]
  {::f (future (.Run app (::url config)))
   ::app app})

(defn map-get [^WebApplication app route f]
  (EndpointRouteBuilderExtensions/MapGet app route (http/request-handler (fn [http-context]
                                                                           (f http-context))))
  app)

(defn map-post [^WebApplication app route f]
  (EndpointRouteBuilderExtensions/MapPost app route (http/request-handler (fn [http-context]
                                                                            (f http-context))))
  app)

(defn map-health-checks [app route]
  (HealthCheckEndpointRouteBuilderExtensions/MapHealthChecks app route)
  app)
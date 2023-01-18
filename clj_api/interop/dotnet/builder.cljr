(ns clj-api.interop.dotnet.builder
  (:import [Microsoft.AspNetCore.Builder EndpointRouteBuilderExtensions HealthCheckEndpointRouteBuilderExtensions]
           [Microsoft.AspNetCore.Http HttpResponseJsonExtensions]
           [System.Threading.Tasks Task]
           [Microsoft.AspNetCore.Server.Kestrel.Core KestrelServerOptions]
           [Microsoft.AspNetCore.Hosting WebHostBuilderKestrelExtensions]
           [System.Threading CancellationTokenSource]
           [Microsoft.AspNetCore.Builder WebApplication WebApplicationBuilder EndpointRouteBuilderExtensions HealthCheckEndpointRouteBuilderExtensions]
           [Microsoft.AspNetCore.Http RequestDelegate HttpResponseWritingExtensions HttpContext]
           [Microsoft.Extensions.DependencyInjection
            HealthCheckServiceCollectionExtensions
            HealthChecksBuilderDelegateExtensions]
           [Microsoft.Extensions.Diagnostics.HealthChecks HealthCheckResult]))

(defn add-health-checks [builder checks]
  (let [x (-> builder
              .-Services
              (HealthCheckServiceCollectionExtensions/AddHealthChecks))]
    (doseq [check checks]
      (HealthChecksBuilderDelegateExtensions/AddCheck
       x
       ^String (:health-check/name check)
       (gen-delegate |System.Func`1[Microsoft.Extensions.Diagnostics.HealthChecks.HealthCheckResult]|[]
                      (let [result ((:health-check/fn check))]
                        (if (= :unhealthy (:health-check/status result))
                          (HealthCheckResult/Unhealthy (:health-check/message result) nil nil)
                          (HealthCheckResult/Healthy (:health-check/message result) nil))))
       ["tags"])))
  builder)

  #_{:clj-kondo/ignore [:unresolved-symbol]}
(defn allow-synchronous-io [builder]
  (-> builder
      .-WebHost
      (WebHostBuilderKestrelExtensions/ConfigureKestrel (gen-delegate |System.Action`1[Microsoft.AspNetCore.Server.Kestrel.Core.KestrelServerOptions]| [options]
                                                                      (set! (.-AllowSynchronousIO options) true))))
  builder)
(ns user)

(defn try-load-file [file-path]
  (try
    (assembly-load-file file-path)
    (catch Exception _)))

(defn load-all-assemblies-in-directory [directory-path]
  (println "Loading assemblies in: " directory-path)
  (doseq [file-path (->> (System.IO.Directory/GetFiles directory-path)
                         (filter (fn [file-name] (clojure.string/includes? file-name ".dll"))))]
    (try-load-file file-path)))

;; LOAD .NET, use your path.
(load-all-assemblies-in-directory "/usr/share/dotnet/shared/Microsoft.AspNetCore.App/6.0.13")
(load-all-assemblies-in-directory "/usr/share/dotnet/shared/Microsoft.NETCore.App/6.0.13")

(ns clj-api.main
  (:import
   [System.Threading.Tasks Task]
   [System.Threading CancellationTokenSource]
   [Microsoft.AspNetCore.Http RequestDelegate HttpResponseWritingExtensions HttpContext]
   [Microsoft.AspNetCore.Routing IEndpointRouteBuilder]
   [Microsoft.AspNetCore.Builder WebApplication WebApplicationBuilder EndpointRouteBuilderExtensions]))

(defn get-dev-config []
  {:http/web-server-url "http://localhost:8080"})

(defn run-app [^WebApplication app config]
  (.Run app (:http/web-server-url config)))

(defn ^RequestDelegate request-handler [f]
  #_{:clj-kondo/ignore [:unresolved-symbol]}
  (gen-delegate RequestDelegate [^HttpContext http-context]
                (Task/Run (gen-delegate System.Action []
                                        (let [response (.Response http-context)
                                              cancellation-token-source (new CancellationTokenSource)
                                              token (.Token cancellation-token-source)]
                                          (HttpResponseWritingExtensions/WriteAsync response (f http-context) token))))))

(defn configure-app [^WebApplication app]
  (let [_ (EndpointRouteBuilderExtensions/Map app "/" (request-handler (fn [http-context]
                                                                         "Hello, world!")))]
    app))

;; TODO
(defn -main [& args])

(comment

  (def app (-> (WebApplication/CreateBuilder)
               (.Build)
               configure-app))
  ;;; >> Microsoft.Scripting.ArgumentTypeException: "expected IEndpointRouteBuilder, got WebApplication"

  (-> (WebApplication/CreateBuilder)
      (.Build)
      class
      (.GetInterfaces))

  (def state (atom app))


  (def config (get-dev-config))

  (future
    (run-app app config))
  
)





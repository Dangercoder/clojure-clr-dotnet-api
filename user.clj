(ns user
  (:require [clojure.string]))

(defn load-all-assemblies-in-directory [directory-path]
  (println "Loading assemblies in: " directory-path)
  (doseq [file-path (->> (System.IO.Directory/GetFiles directory-path)
                         (filter (fn [file-name] (clojure.string/includes? file-name ".dll"))))]
    (try
      #_{:clj-kondo/ignore [:unresolved-symbol]}
      (assembly-load-from file-path)
      (catch Exception _))))

(def assembly-directory-microsoft-netcore-app (-> (int 32)
                                                  (.GetType)
                                                  .Assembly
                                                  .-Location
                                                  System.IO.Path/GetDirectoryName))

(def assembly-directory-microsoft-asp-netcore-app (clojure.string/replace assembly-directory-microsoft-netcore-app #"Microsoft.NETCore.App" "Microsoft.AspNetCore.App"))

(load-all-assemblies-in-directory assembly-directory-microsoft-netcore-app)
(load-all-assemblies-in-directory assembly-directory-microsoft-asp-netcore-app)

(def home-path (System.Environment/GetEnvironmentVariable "HOME"))

(defn print-all-assemblies
  []
  (doseq [assembly (seq (.GetAssemblies (System.AppDomain/CurrentDomain)))]
    (println (.FullName assembly))))

;; packages. They've all been added via `dotnet install` and then `dotnet restore` to download the packages.
(def packages ["clojure.data.priority-map/1.1.0/lib/netstandard2.1/clojure.data.priority-map.dll"
               "clojure.core.cache/1.0.226/lib/netstandard2.1/clojure.core.cache.dll"
               "clojure.core.memoize/1.0.257/lib/netstandard2.1/clojure.core.memoize.dll"
               "clr.tools.reader/1.3.7/lib/netstandard2.1/clojure.tools.reader.dll"
               "clojure.tools.reader/1.3.7/lib/netstandard2.1/clojure.tools.reader.dll"
               "clojure.tools.analyzer/1.1.1/lib/netstandard2.1/clojure.tools.analyzer.dll"
               "clojure.tools.analyzer.clr/1.2.4/lib/netstandard2.1/clojure.tools.analyzer.clr.dll"
               "clojure.core.async/1.6.674/lib/netstandard2.1/clojure.core.async.dll"
               "clojure.tools.nrepl/0.1.0-alpha1/lib/netstandard2.1/clojure.tools.nrepl.dll"])

(def assemblies (doall (map (fn [package]
                              #_{:clj-kondo/ignore [:unresolved-symbol]}
                              (assembly-load-from (str home-path "/.nuget/packages/" package))) packages)))
(doseq [assembly assemblies]
  (println "Loaded:" assembly))



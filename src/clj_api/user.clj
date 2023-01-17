(ns user)
(println "Loading user.")

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
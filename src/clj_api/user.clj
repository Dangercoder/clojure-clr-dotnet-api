(ns user)
(println "Loading user.clj")

(defn try-load-file [file-path]
  (try
    (assembly-load-file file-path)
    (catch Exception _)))

(defn load-all-assemblies-in-directory [directory-path]
  (println "Loading assemblies in: " directory-path)
  (doseq [file-path (->> (System.IO.Directory/GetFiles directory-path)
                         (filter (fn [file-name] (clojure.string/includes? file-name ".dll"))))]
    (try-load-file file-path)))


(def assembly-directory-microsoft-netcore-app (-> (int 32)
                                                  (.GetType)
                                                  .Assembly
                                                  .-Location
                                                  System.IO.Path/GetDirectoryName))

(def assembly-directory-microsoft-asp-netcore-app (clojure.string/replace assembly-directory-microsoft-netcore-app #"Microsoft.NETCore.App" "Microsoft.AspNetCore.App"))

(load-all-assemblies-in-directory assembly-directory-microsoft-netcore-app)
(load-all-assemblies-in-directory assembly-directory-microsoft-asp-netcore-app)
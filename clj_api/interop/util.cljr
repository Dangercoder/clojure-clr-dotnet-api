(ns clj-api.interop.util)

(defn print-methods
  [v]
  (map (fn [v] (.-Name v))(.GetMethods (.GetType v))))
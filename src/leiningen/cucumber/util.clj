(ns leiningen.cucumber.util
  (:use [clojure.java.io])
  (:import [cucumber.runtime.clj Backend])
  (:import [cucumber.runtime.io MultiLoader])
  (:import [cucumber.runtime.model CucumberFeature])
  (:import [cucumber.runtime RuntimeOptions CucumberException]))

(defn- create-runtime-options [feature-paths glue-paths target-path args]
  (let [runtime-options (RuntimeOptions. (vec args))]
    (when (.. runtime-options (getFeaturePaths) (isEmpty))
      (.. runtime-options (getFeaturePaths) (addAll feature-paths)))
    (when (.. runtime-options (getGlue) (isEmpty))
      (.. runtime-options (getGlue) (addAll glue-paths)))
    runtime-options))


(defn- create-runtime [runtime-options]
  (let [classloader (.getContextClassLoader (Thread/currentThread))
        resource-loader (MultiLoader. classloader)
        backend (Backend. resource-loader)]
    (cucumber.runtime.Runtime. resource-loader classloader [backend] runtime-options)))

(defn run-cucumber! [feature-paths glue-paths target-path args]
  (let [runtime-options (create-runtime-options feature-paths glue-paths
                                                target-path args)
        runtime (create-runtime runtime-options)]
    (println "Running cucumber...")
    (println "Looking for features in: " (vec (.getFeaturePaths runtime-options)))
    (println "Looking for glue in: " (vec (.getGlue runtime-options)))
    (try
      (.run runtime)
      (catch CucumberException e
        (println (.getMessage e))))
    runtime))

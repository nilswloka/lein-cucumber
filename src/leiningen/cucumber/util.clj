(ns leiningen.cucumber.util
  (:use [clojure.java.io])
  (:import [cucumber.runtime.formatter FormatterFactory])
  (:import [cucumber.runtime.io FileResourceLoader])
  (:import [cucumber.runtime.model CucumberFeature])
  (:import [cucumber.runtime RuntimeOptions CucumberException]))

(defn- create-runtime-options [feature-paths glue-paths target-path args]
  (let [runtime-options (RuntimeOptions. (java.util.Properties.)
                                         (into-array String args))
        formatter-factory (FormatterFactory.)]
    (when (.. runtime-options featurePaths (isEmpty))
      (.. runtime-options featurePaths (addAll feature-paths)))
    (when (.. runtime-options glue (isEmpty))
      (.. runtime-options glue (addAll glue-paths)))
    (doto (.formatters runtime-options)
      (.add (.create formatter-factory (str "pretty:"
                                            (.getAbsolutePath (file target-path
                                                                    "test-reports"
                                                                    "cucumber.out")))))
      (.add (.create formatter-factory "progress")))
    runtime-options))

(defn- create-runtime [runtime-options]
  (let [classloader (.getContextClassLoader (Thread/currentThread))
        resource-loader (FileResourceLoader.)]
    (cucumber.runtime.Runtime. resource-loader classloader runtime-options)))

(defn run-cucumber! [feature-paths glue-paths target-path args]
  (let [runtime-options (create-runtime-options feature-paths glue-paths
                                                target-path args)
        runtime (create-runtime runtime-options)]
    (println "Running cucumber...")
    (println "Looking for features in: " (vec (.featurePaths runtime-options)))
    (println "Looking for glue in: " (vec (.glue runtime-options)))
    (try
      (.run runtime)
      (catch CucumberException e
        (println (.getMessage e))))
    runtime))

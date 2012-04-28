(ns leiningen.cucumber.util
  (:use [clojure.java.io])
  (:import [cucumber.formatter CucumberPrettyFormatter ProgressFormatter])
  (:import [cucumber.io FileResourceLoader])
  (:import [cucumber.runtime.model CucumberFeature])
  (:import [cucumber.runtime RuntimeOptions CucumberException]))

(defn- report-writer [target-path]
  (let [report-file (file target-path "test-reports" "cucumber.out")]
    (make-parents report-file)
    (writer report-file)))

(defn- create-runtime-options [feature-paths glue-paths target-path args]
  (let [runtime-options (RuntimeOptions. (into-array String args))]
    (when (.. runtime-options featurePaths (isEmpty))
      (.. runtime-options featurePaths (addAll feature-paths)))
    (when (.. runtime-options glue (isEmpty))
      (.. runtime-options glue (addAll glue-paths)))
    (doto (.formatters runtime-options)
      (.add (CucumberPrettyFormatter. (report-writer target-path)))
      (.add (ProgressFormatter. *out*)))
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

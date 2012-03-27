(ns leiningen.cucumber.util
  (:use [clojure.java.io])
  (:import [cucumber.formatter CucumberPrettyFormatter ProgressFormatter])
  (:import [cucumber.io FileResourceLoader])
  (:import [cucumber.runtime.model CucumberFeature])
  (:import [cucumber.runtime RuntimeOptions]))

(defn- report-writer [target-path]
  (let [report-file (file target-path "test-reports" "cucumber.out")]
    (make-parents report-file)
    (writer report-file)))

(defn- create-runtime-options [feature-paths glue-paths target-path]
  (let [runtime-options (RuntimeOptions. (into-array String []))]
    (.addAll (.featurePaths runtime-options) feature-paths)
    (.addAll (.glue runtime-options) glue-paths)
    (doto (.formatters runtime-options)
      (.add (CucumberPrettyFormatter. (report-writer target-path)))
      (.add (ProgressFormatter. *out*)))
    runtime-options))

(defn- create-runtime [runtime-options]
  (let [classloader (.getContextClassLoader (Thread/currentThread))
        resource-loader (FileResourceLoader.)]
    (cucumber.runtime.Runtime. resource-loader classloader runtime-options)))

(defn run-cucumber! [feature-paths glue-paths target-path]
  (let [runtime-options (create-runtime-options feature-paths glue-paths target-path)
        runtime (create-runtime runtime-options)]
    (.run runtime)
    runtime))

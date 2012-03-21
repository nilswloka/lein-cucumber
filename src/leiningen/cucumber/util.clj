(ns leiningen.cucumber.util
  (:use [clojure.java.io])
  (:import [cucumber.formatter CucumberPrettyFormatter])
  (:import [cucumber.io FileResourceLoader])
  (:import [cucumber.runtime.model CucumberFeature])
  (:import [cucumber.runtime RuntimeOptions]))

(defn- report-writer [target-path]
  (let [report-file (file target-path "test-reports" "cucumber.out")]
    (make-parents report-file)
    (writer report-file)))

(defn- create-runtime-options [feature-paths glue-paths target-path]
  (let [out (report-writer target-path)
        formatter (CucumberPrettyFormatter. out)
        runtime-options
        (proxy [RuntimeOptions] [(into-array String [])]
          (reporter [classloader] formatter)
          (formatter [classloader] formatter)
          (cucumberFeatures [resource-loader] (CucumberFeature/load resource-loader feature-paths [])))]
    (set! (. runtime-options glue) (java.util.ArrayList. glue-paths))
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

(ns leiningen.cucumber.util
  (:use [clojure.java.io])
  (:import [gherkin.formatter PrettyFormatter])
  (:import [cucumber.formatter FormatterFactory MultiFormatter])
  (:import [cucumber.io FileResourceLoader])
  (:import [cucumber.cli DefaultRuntimeFactory])
  (:import [cucumber.runtime.snippets SummaryPrinter])
  (:import [java.io File])
  (:import [java.util ArrayList]))

(defn- report-writer []
  (let [report-directory (file "test-reports")
        report-file (file report-directory "cucumber.out")]
    (make-parents report-file)
    (writer report-file)))

(defn- create-multi-formatter []
  (let [classloader (.getContextClassLoader (Thread/currentThread))
        formatter-factory (FormatterFactory. classloader)
        multi-formatter (MultiFormatter. classloader)
        out (report-writer)
        formatter (PrettyFormatter. out true true)]
    (doto multi-formatter
      (.add formatter)
      (.add (.createFormatter formatter-factory "progress" System/out)))
    multi-formatter))

(defn- create-runtime [stepdef-paths]
  (let [runtime-factory (DefaultRuntimeFactory.)
        classloader (.getContextClassLoader (Thread/currentThread))
        glue-paths (ArrayList. stepdef-paths)
        file-resource-loader (FileResourceLoader.)]
    (.createRuntime runtime-factory file-resource-loader glue-paths classloader false)))

(defn- gen-stepdef-paths [feature-paths]
  (map (fn [path]
         (format "%s%sstep_definitions" path (if (= (last path) \/) "" "/")))
       feature-paths))

(defn run-cucumber! [feature-paths]
  (let [multi-formatter (create-multi-formatter)
        feature-paths (if (some identity feature-paths)
                        feature-paths ["features"])
        runtime (create-runtime (gen-stepdef-paths feature-paths))
        formatter (.formatterProxy multi-formatter)
        reporter (.reporterProxy multi-formatter)
        summary-printer (SummaryPrinter. System/out)]
    (.run runtime (ArrayList. feature-paths) (ArrayList.) formatter reporter)
    (.done formatter)
    (.print summary-printer runtime)
    (.close formatter)
    runtime))

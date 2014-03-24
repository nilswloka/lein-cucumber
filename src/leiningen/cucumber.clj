(ns leiningen.cucumber
  (:use [clojure.java.io])
  (:use [leiningen.core.eval :only [eval-in-project]])
  (:require [leiningen.core.project :as project])
  (:import [cucumber.runtime RuntimeOptions]))

(defn- configure-feature-paths [runtime-options feature-paths]
  (when (.. runtime-options (getFeaturePaths) (isEmpty))
    (.. runtime-options (getFeaturePaths) (addAll feature-paths))))

(defn- configure-glue-paths [runtime-options glue-paths feature-paths]
  (when (.. runtime-options (getGlue) (isEmpty))
    (if (empty? glue-paths)
      (.. runtime-options (getGlue) (addAll (into [] (map #(str (file % "step_definitions/")) feature-paths))))
      (.. runtime-options (getGlue) (addAll glue-paths)))))

(defn create-partial-runtime-options [{:keys [cucumber-feature-paths target-path cucumber-glue-paths] :or {cucumber-feature-paths ["features"]}} args]
  (let [runtime-options (RuntimeOptions. (vec args))]
    (configure-feature-paths runtime-options cucumber-feature-paths)
    (configure-glue-paths runtime-options cucumber-glue-paths (.getFeaturePaths runtime-options))
    runtime-options))

(defn cucumber
  "Runs Cucumber features in test/features with glue in test/features/step_definitions"
  [project & args]
  (binding [leiningen.core.main/*exit-process?* true]
    (let [runtime (gensym "runtime")
          runtime-options (create-partial-runtime-options project args)
          glue-paths (vec (.getGlue runtime-options))
          feature-paths (vec (.getFeaturePaths runtime-options))
          target-path (:target-path project)
          project (project/merge-profiles project [:test])]
      (eval-in-project
       (-> project
           (update-in [:dependencies] conj
                      ['lein-cucumber "1.0.3"]
                      ['info.cukes/cucumber-clojure "1.1.6"])
           (update-in [:source-paths] (partial apply conj) glue-paths))
       `(do
          (let [~runtime (leiningen.cucumber.util/run-cucumber! ~feature-paths ~glue-paths ~target-path ~(vec args))]
            (leiningen.core.main/exit (.exitStatus ~runtime))))
       '(require 'leiningen.cucumber.util 'leiningen.core.main)))))

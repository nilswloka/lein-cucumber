(ns leiningen.cucumber
  (:use [clojure.java.io])
  (:use [leiningen.core.eval :only [eval-in-project]]))

(defn cucumber
  "Runs Cucumber features in test/features with glue in test/features/step_definitions"
  [project & args]
  (let [runtime (gensym "runtime")
        feature-paths (into [] (get project :cucumber-feature-paths ["features"]))
        glue-paths (into [] (map #(str (file % "step_definitions/")) feature-paths))
        target-path (:target-path project)]
    (eval-in-project
     (-> project
         (update-in [:dependencies] conj
                    ['lein-cucumber "1.0.0.M2"]
                    ['info.cukes/cucumber-clojure "1.0.4"])
         (update-in [:source-paths] (partial apply conj) glue-paths))
     `(do
        (let [~runtime (leiningen.cucumber.util/run-cucumber! ~feature-paths ~glue-paths ~target-path ~(vec args))]
          (leiningen.core.main/exit (.exitStatus ~runtime))))
     '(require 'leiningen.cucumber.util 'leiningen.core.main))))

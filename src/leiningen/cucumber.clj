(ns leiningen.cucumber
  (:use [leiningen.compile :only [eval-in-project]])
  (:require [leiningen.core :as core]))

(defn cucumber
  "Runs Cucumber features in test/features with glue in test/features/step_definitions"
  [project]
  (let [runtime (gensym "runtime")
        feature-paths (:cucumber-feature-path project)]
    (eval-in-project
     project
     `(do
        (let [~runtime (leiningen.cucumber.util/run-cucumber! ~feature-paths)]
          (println)
          ~(when-not core/*interactive?*
             `(System/exit (.exitStatus ~runtime)))))
     nil
     nil
     '(require 'leiningen.cucumber.util))))

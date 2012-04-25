(use 'clojure.java.io)
(use 'clojure.java.shell)

(def feature-directory (atom "features"))
(def feature-file (atom "test.feature"))
(def feature-content "Feature: test-feature\n  Scenario: A test\n    Given a step\n")
(def step-directory (atom "step_definitions"))
(def step-content
  '(Given #"^a step$" [] (println "{{{STEP EXECUTED}}}")))
(def result (atom ""))

(defn project-configuration []
  '(defproject test-project "0.1.0"
     :description "A test project"
     :plugins [[lein-cucumber "1.0.0.M2"]]))

(Given #"^a lein-cucumber project without special configuration$" []
       (let [project-file (file "target" "test_project" "project.clj")]
         (make-parents project-file)
         (delete-file project-file true)
         (spit project-file (project-configuration))))

(Given #"^no features in the feature directory$" []
       (let [feature-file (file "target" "test_project" @feature-directory @feature-file)]
         (make-parents feature-file)
         (delete-file feature-file true)))

(Given #"^a feature in the feature directory$" []
       (let [feature-file (file "target" "test_project" @feature-directory @feature-file)]
         (make-parents feature-file)
         (delete-file feature-file true)
         (spit feature-file feature-content)))

(Given #"^no step definitions in the step definition directory$" []
       (let [step-file (file "target" "test_project" @feature-directory @step-directory "test_steps.clj")]
         (make-parents step-file)
         (delete-file step-file true)))

(Given #"^a step definition in the step definition directory$" []
       (let [step-file (file "target" "test_project" @feature-directory @step-directory "test_steps.clj")]
         (make-parents step-file)
         (delete-file step-file true)
         (spit step-file step-content)))

(When #"^I run \"([^\"]*)\"$" [command]
      (reset! result (apply sh (concat (re-seq #"\S+\b" command) [:dir (file "target" "test_project") :env (into {} (System/getenv))]))))

(Then #"^the output should be an error message including \"([^\"]*)\"$" [expected-output]
      (do (print @result)
          (assert (.contains (:err @result) expected-output))))

(Then #"^the output should be a success message including \"([^\"]*)\"$" [expected-output]
      (do (print @result)
          (assert (.contains (:out @result) expected-output))))

(Then #"^the step should be executed" []
      (do (print @result)
          (assert (.contains (:out @result) "{{{STEP EXECUTED}}}"))))

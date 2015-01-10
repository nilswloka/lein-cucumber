(use 'clojure.java.io)
(use 'clojure.java.shell)
(use 'leiningen.cucumber)
(require '[leiningen.core.project :as project])
(require '[leiningen.core.user :as user])
(import '[org.apache.commons.io FileUtils])
(import '[java.io StringWriter])

(def project-directory
  (file "target" "test_project"))

(def feature-content
  "Feature: test-feature\n  Scenario: A test\n    Given a step\n")

(def step-content
  '(Given #"^a step$" [] (println "{{{STEP EXECUTED}}}")))

(def result (atom ""))

(defn- project-configuration
  ([] '(defproject test-project "0.1.0"
         :description "A test project"))
  ([parameters]
     (seq (concat (project-configuration) parameters))))

(defn- create-project [project-file-content]
  (let [project-file (file project-directory "project.clj")]
    (.mkdirs project-directory)
    (FileUtils/cleanDirectory project-directory)
    (spit project-file project-file-content)))

(defn- create-empty-directory [path]
  (let [empty-directory (file project-directory path)]
    (.mkdirs empty-directory)
    (FileUtils/cleanDirectory empty-directory)))

(defn- create-file [path name content]
  (let [the-file (file project-directory path name)]
    (make-parents the-file)
    (delete-file the-file true)
    (spit the-file content)))

(defn- read-project []
  (with-redefs [user/profiles (constantly {})]
    (project/read "target/test_project/project.clj")))

(defn- writing-to-result [f]
  (let [out-writer (java.io.StringWriter.)]
    (with-redefs [*out* out-writer]
      (f)
      (reset! result (.toString out-writer)))))

(defn- assert-output-includes [text]
  (assert (.contains @result text)))

(Given #"^a lein-cucumber project without special configuration$" []
       (create-project (project-configuration)))

(Given #"^a lein-cucumber project with the following parameters:$" [parameter-table]
       (let [parameters (map (comp read-string first) (.raw parameter-table))]
         (create-project (project-configuration parameters))))

(Given #"^no features in the \"([^\"]*)\" directory$" [path]
       (create-empty-directory path))

(Given #"^no step definitions in the \"([^\"]*)\" directory$" [path]
       (create-empty-directory path))

(Given #"^a feature in the \"([^\"]*)\" directory$" [path]
       (create-file path "test.feature" feature-content))

(Given #"^a step definition in the \"([^\"]*)\" directory$" [path]
       (create-file path "test_steps.clj" step-content))

(When #"^I run lein-cucumber without command line arguments$" []
      (writing-to-result
       (fn [] (cucumber (read-project)))))

(When #"^I run lein-cucumber with arguments \"([^\"]*)\"$" [args]
      (writing-to-result
       (fn [] (apply cucumber (concat (vector (read-project)) (re-seq #"\S+\b" args))))))

(When #"^I run lein-cucumber with default output$" []
      (cucumber (read-project)))


(Then #"^the output should include \"([^\"]*)\"$" [expected-output]
      (assert-output-includes expected-output))

(Then #"^the step should be executed" []
      (assert-output-includes "{{{STEP EXECUTED}}}"))

(Then #"^there should be an output file in the \"([^\"]*)\" directory$" [arg1]
      (assert (.exists (as-file "target/test_project/target/test-reports/cucumber.out"))))

(Then #"^there should be an html file in the \"([^\"]*)\" directory$" [arg1]
      (assert (.exists (as-file "target/test_project/target/test-reports/index.html"))))

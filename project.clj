(defproject lein-cucumber "1.0.1-SNAPSHOT"
  :description "Run cucumber-jvm specifications with leiningen"
  :dependencies [[info.cukes/cucumber-clojure "1.0.4"]
                 [leiningen-core "2.0.0-preview3"]]
  :profiles {:test {:dependencies [[commons-io "2.0"]
                                   [lein-cucumber "1.0.1-SNAPSHOT"]]}}
  :plugins [[lein-cucumber "1.0.0"]]
  :eval-in :leiningen
  :license {:name "Unlicense"
            :url "http://unlicense.org/"
            :distribution :repo})

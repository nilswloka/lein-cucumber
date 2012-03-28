(defproject lein-cucumber "1.0.0.M1-SNAPSHOT"
  :description "Run cucumber-jvm specifications with leiningen"
  :dependencies [[info.cukes/cucumber-clojure "1.0.0"]
                 [leiningen-core "2.0.0-preview2"]]
  :eval-in :leiningen
  :license {:name "Unlicense"
            :url "http://unlicense.org/"
            :distribution :repo})

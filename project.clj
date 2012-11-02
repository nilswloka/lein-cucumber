(defproject lein-cucumber "1.0.1"
  :description "Run cucumber-jvm specifications with leiningen"
  :dependencies [[info.cukes/cucumber-clojure "1.0.14"]
                 [leiningen-core "2.0.0-preview10"]]
  :profiles {:test {:dependencies [[commons-io "2.0"]]}}
  :plugins [[lein-cucumber "1.0.1"]]
  :eval-in :leiningen
  :license {:name "Unlicense"
            :url "http://unlicense.org/"
            :distribution :repo})

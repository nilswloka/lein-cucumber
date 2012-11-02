(defproject lein-cucumber "1.0.2-SNAPSHOT"
  :description "Run cucumber-jvm specifications with leiningen"
  :dependencies [[info.cukes/cucumber-clojure "1.0.14"]
                 [leiningen-core "2.0.0-preview10"]
                 [org.clojure/clojure "1.5.0-alpha5"]]
  :profiles {:test {:dependencies [[commons-io "2.0"]]
                    :plugins [[lein-cucumber "1.0.2-SNAPSHOT"]]}}
  :eval-in :leiningen
  :license {:name "Unlicense"
            :url "http://unlicense.org/"
            :distribution :repo})

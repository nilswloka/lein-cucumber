(defproject org.clojars.punkisdead/lein-cucumber "1.0.3"
  :description "Run cucumber-jvm specifications with leiningen"
  :dependencies [[info.cukes/cucumber-clojure "1.1.6"]
                 [leiningen-core "2.0.0"]
                 [org.clojure/clojure "1.5.0-RC3"]]
  :profiles {:cucumber {:dependencies [[commons-io "2.0"]]
                        :plugins [[org.clojars.punkisdead/lein-cucumber "1.0.3"]]}}
  :eval-in :leiningen
  :license {:name "Unlicense"
            :url "http://unlicense.org/"
            :distribution :repo})

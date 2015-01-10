(defproject org.clojars.punkisdead/lein-cucumber "1.0.4"
  :description "Run cucumber-jvm specifications with leiningen"
  :url https://github.com/punkisdead/lein-cucumber
  :dependencies [[info.cukes/cucumber-clojure "1.2.0"]
                 [leiningen-core "2.0.0"]
                 [org.clojure/clojure "1.6.0"]]
  :profiles {:cucumber {:dependencies [[commons-io "2.4"]]
                        :plugins [[org.clojars.punkisdead/lein-cucumber "1.0.4"]]}}
  :eval-in :leiningen
  :license {:name "Unlicense"
            :url "http://unlicense.org/"
            :distribution :repo})

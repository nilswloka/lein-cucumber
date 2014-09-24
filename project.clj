(defproject lein-cucumber "1.0.4-giri"
  :description "Run cucumber-jvm specifications with leiningen"
  :dependencies [[info.cukes/cucumber-clojure "1.1.8"]
                 [leiningen-core "2.0.0"]
                 [org.clojure/clojure "1.5.0-RC3"]]
  :profiles {:cucumber {:dependencies [[commons-io "2.0"]]
                        :plugins [[lein-cucumber "1.0.4-giri"]]}}
  
  :license {:name "Unlicense"
            :url "http://unlicense.org/"
            :distribution :repo}
      )

# lein-cucumber

This is a very simple leiningen plugin for use with [cucumber-jvm](https://github.com/cucumber/cucumber-jvm).

## Usage

1. Add `[lein-cucumber "0.1.0"]` to `:dev-dependencies` in your project.clj
2. Run `lein deps` to fetch all dependencies.
3. Run all Cucumber features with `lein cucumber`

## Please note

In the current version, you cannot specify any configuration options. The following settings are hard-coded into the plugin:

* A progress report will be printed to the console. 
* The complete report (formatted with `PrettyFormatter`) will be written to `test-reports/cucumber.out`. Consider adding `:extra-files-to-clean ["test-reports"]` to your project.clj.
* Leiningen will exit with the exit status of the cucumber-jvm [runtime](https://github.com/cucumber/cucumber-jvm/blob/master/core/src/main/java/cucumber/runtime/Runtime.java).
* Put your `.feature` files into `features/` (feature-paths can be configured in project.clj using the `:cucumber-feature-path` parameter, e.g `:cucumber-feature-path ["test/features/"]`.)
* Put your step definitions into `features/step_definitions/`

See https://github.com/cucumber/cucumber-jvm/tree/master/clojure/src/test/resources/cucumber/runtime/clojure for an example specification.

## Unlicense

Written by Nils Wloka, 2012. For licensing information, see UNLICENSE.
Please have a look at http://unlicense.org if you plan to contribute.

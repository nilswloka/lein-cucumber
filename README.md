# lein-cucumber

This is a very simple leiningen plugin for use with [cucumber-jvm](https://github.com/cucumber/cucumber-jvm).

## Usage

1. Add `[lein-cucumber "1.0.0.M1-SNAPSHOT"]` to `:plugins` in your project.clj
2. Run `lein deps` to fetch all dependencies.
3. Run all Cucumber features with `lein cucumber`

## Please note

lein-cucumber requires Leiningen 2.

## Configuration

You can configure feature paths like this:

* Add a `:cucumber-feature-paths` parameter to your project.clj (e.g. `:cucumber-feature-paths ["test/features/"]`).
* By default, lein-cucumber looks for your `.feature` files in the `features/` directory.
* Step definitions will be loaded from the `step_definitions/` directories inside your feature directories.

## Other settings

 The following settings are hard-coded into the plugin:

* A summary report will be printed to the console. 
* The complete report (formatted with `CucumberPrettyFormatter`) will be written to `test-reports/cucumber.out` inside your project's target directory (usually `target/`).
* Leiningen will exit with the exit status of the cucumber-jvm [runtime](https://github.com/cucumber/cucumber-jvm/blob/master/core/src/main/java/cucumber/runtime/Runtime.java).

See https://github.com/nilswloka/cucumber-jvm/tree/new-clojure-example/examples/clojure_cukes for an example project.

## Unlicense

Written by Nils Wloka, 2012. For licensing information, see UNLICENSE.
Please have a look at http://unlicense.org if you plan to contribute.

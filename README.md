[![Build Status](https://secure.travis-ci.org/nilswloka/lein-cucumber.png)](http://travis-ci.org/nilswloka/lein-cucumber)

# lein-cucumber

This is a leiningen plugin for use with [cucumber-jvm](https://github.com/cucumber/cucumber-jvm).

## Usage

1. Add `[lein-cucumber "1.0.2"]` to `:plugins` in your project.clj
2. Run `lein deps` to fetch all dependencies.
3. Run all Cucumber features with `lein2 cucumber`

## Please note

lein-cucumber requires Leiningen 2.

## Configuration

Feature paths are resolved in the following order:

1. Command line options (e.g. `lein2 cucumber my-features`) override all other settings.
2. If no command line options for feature paths are given, the `:cucumber-feature-paths` parameter in your project.clj will be used (e.g. `:cucumber-feature-paths ["test/features/"]`).
3. If neither command line options nor a parameter is used, lein-cucumber looks for features in the `features/` directory.

Glue paths are resolved similarily:

1. Command line options (e.g. `lein2 cucumber --glue somewhere/my_stepdefs`) override all other settings.
2. If no command line options for glue paths are given, step definitions will be loaded from `step_definitions/` directories inside your feature directories.

## Other settings

 The following settings are hard-coded into the plugin:

* A summary report will be printed to the console. 
* The complete report (formatted with `CucumberPrettyFormatter`) will be written to `test-reports/cucumber.out` inside your project's target directory (usually `target/`).
* Leiningen will exit with the exit status of the cucumber-jvm [runtime](https://github.com/cucumber/cucumber-jvm/blob/master/core/src/main/java/cucumber/runtime/Runtime.java).

See https://github.com/nilswloka/cucumber-jvm/tree/new-clojure-example/examples/clojure_cukes for an example project.

## Note

If you like lein-cucumber, consider endorsing me at [coderwall](http://coderwall.com/nilswloka): 

[![endorse](http://api.coderwall.com/nilswloka/endorsecount.png)](http://coderwall.com/nilswloka)

## Unlicense
Written by Nils Wloka, 2012. For licensing information, see UNLICENSE.

Contributions by [Robert P. Levy](https://github.com/rplevy-draker), [Michael van Acken](https://github.com/mva), [Jeroen van Dijk](https://github.com/jeroenvandijk) and [Ben Poweski](https://github.com/bpoweski). Please have a look at http://unlicense.org if you plan to contribute.


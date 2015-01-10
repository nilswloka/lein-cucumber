[![Build Status](https://secure.travis-ci.org/punkisdead/lein-cucumber.png)](http://travis-ci.org/punkisdead/lein-cucumber)

# lein-cucumber

This is a leiningen plugin for use with [cucumber-jvm](https://github.com/cucumber/cucumber-jvm).
This is a fork of [lein-cucumber](http://github.com/nilswloka/lein-cucumber) with more up to date dependencies.

[![Clojars Project](http://clojars.org/org.clojars.punkisdead/lein-cucumber/latest-version.svg)](http://clojars.org/org.clojars.punkisdead/lein-cucumber)

## Usage

1. Add `[lein-cucumber "1.0.4"]` to `:plugins` in your project.clj
2. Run `lein deps` to fetch all dependencies.
3. Run all Cucumber features with `lein cucumber`

## Please note

lein-cucumber requires Leiningen 2.

## Configuration

Feature paths are resolved in the following order:

1. Command line options (e.g. `lein cucumber my-features`) override all other settings.
2. If no command line options for feature paths are given, the `:cucumber-feature-paths` parameter in your project.clj will be used (e.g. `:cucumber-feature-paths ["test/features/"]`).
3. If neither command line options nor a parameter is used, lein-cucumber looks for features in the `features/` directory.

Glue paths are resolved similarily:

1. Command line options (e.g. `lein cucumber --glue somewhere/my_stepdefs`) override all other settings.
2. If no command line options for glue paths are given, step definitions will be loaded from `step_definitions/` directories inside your feature directories.

Formatted output

1. Results are only printed to the console unless you specify a formatter
2. To create an HTML report you can run the plugin with the following command `lein cucumber --plugin html:target/test-reports`

## Other settings

 The following settings are hard-coded into the plugin:

* A summary report will be printed to the console.
* Leiningen will exit with the exit status of the cucumber-jvm [runtime](https://github.com/cucumber/cucumber-jvm/blob/master/core/src/main/java/cucumber/runtime/Runtime.java).

See https://github.com/nilswloka/cucumber-jvm/tree/new-clojure-example/examples/clojure_cukes for an example project.

## Note

If you like lein-cucumber, consider endorsing me at [coderwall](http://coderwall.com/punkisdead):

[![endorse](http://api.coderwall.com/punkisdead/endorsecount.png)](http://coderwall.com/punkisdead)

## Unlicense
Written by Jeremy Anderson, 2015. For licensing information, see UNLICENSE.

Please have a look at http://unlicense.org if you plan to contribute.

Feature: lein-cucumber works

  Scenario: No features
    Given a lein-cucumber project without special configuration
    And no features in the "features" directory
    And no step definitions in the "features/step_definitions" directory
    When I run lein-cucumber without command line arguments
    Then the output should include "No features found"

  Scenario: Features, but no step definitions
    Given a lein-cucumber project without special configuration
    And a feature in the "features" directory
    And no step definitions in the "features/step_definitions" directory
    When I run lein-cucumber without command line arguments
    Then the output should include "You can implement missing steps with the snippets below:"

  Scenario: Features and step definitions
    Given a lein-cucumber project without special configuration
    And a feature in the "features" directory
    And a step definition in the "features/step_definitions" directory
    When I run lein-cucumber without command line arguments
    Then the step should be executed
    And the output should include "."

  Scenario: Specify feature directory in project.clj
    Given a lein-cucumber project with the following parameters:
      | :cucumber-feature-paths |
      | ["test/features/"]      |
    And a feature in the "test/features/" directory
    And a step definition in the "test/features/step_definitions" directory
    When I run lein-cucumber without command line arguments
    Then the step should be executed

  Scenario: Specify feature directory via command line
    Given a lein-cucumber project without special configuration
    And a feature in the "foo" directory
    And a step definition in the "foo/step_definitions" directory
    When I run lein-cucumber with arguments "foo"
    Then the step should be executed

  Scenario: Specify glue directory via command line
    Given a lein-cucumber project without special configuration
    And a feature in the "features" directory
    And a step definition in the "foo" directory
    When I run lein-cucumber with arguments "--glue foo"
    Then the step should be executed

  Scenario: Creates an output file 
    Given a lein-cucumber project without special configuration
    And a feature in the "features" directory
    And a step definition in the "features/step_definitions" directory
    When I run lein-cucumber without command line arguments
    Then there should be an output file in the "target/test-reports" directory

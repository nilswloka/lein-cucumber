Feature: lein-cucumber works

  Scenario: No features
    Given a lein-cucumber project without special configuration
    And no features in the feature directory
    When I run "lein2 cucumber"
    Then the output should be an error message including "No features found"

  Scenario: Features, but no step definitions
    Given a lein-cucumber project without special configuration
    And a feature in the feature directory
    And no step definitions in the step definition directory
    When I run "lein2 cucumber"
    Then the output should be a success message including "You can implement missing steps with the snippets below:"

  Scenario: Features and step definitions
    Given a lein-cucumber project without special configuration
    And a feature in the feature directory
    And a step definition in the step definition directory
    When I run "lein2 cucumber"
    Then the step should be executed
    And the output should be a success message including "."

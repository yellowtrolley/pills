@txn
Feature: See Messages

  Scenario: See another user's products
    Given there is a User
    And the User has added the following products:
    	| name | morningDose | middayDose | nightDose |
    	| "Product 1" | 0.5 | 1.0 | 1.5 |
    	| "Product 2" | 0.5 | 1.0 | 1.5 |
    	| "Product 3" | 0.5 | 1.0 | 1.5 |
    When I visit the page for the User
    Then I should see "Product 1"

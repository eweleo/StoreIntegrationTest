Feature: abc

  Scenario Outline: GET to <string>
    Given remote service instance
    When sending "GET" request to <string>
    Then response should be 200  with body not null
    Examples:
      | string            |
      | "products"        |
      | "inventory"       |
      | "users"           |
      | "orders"          |
      | "choice_products" |


  Scenario: Test products
    Given remote service instance
    Given product's body, name: "product"
    When sending "POST" request to "products" with body
    Then response should be 201  with body not null
    Given product's body, name: "new"
    When sending "PATCH" request to "products" with body
    Then response should be 200  with body not null
    When sending "DELETE" request to "products" with parameter
    Then response for delete should be no content

  Scenario: Test inventory
    Given remote service instance
    Given inventory's body, name: "product", quantity: 100
    When sending "POST" request to "inventory" with body
    Then response should be 201  with body not null
    Given inventory's body, name: "", quantity: 200
    When sending "PATCH" request to "inventory" with body
    Then response should be 200  with body not null
    When sending "DELETE" request to "inventory" with parameter
    Then response for delete should be no content

  Scenario: Test users
    Given remote service instance
    Given user's body, name: "name", surname: "surname"
    When sending "POST" request to "users" with body
    Then response should be 201  with body not null
    Given user's body, name: "newName", surname: "newSurname"
    When sending "PATCH" request to "users" with body
    Then response should be 200  with body not null
    When sending "DELETE" request to "users" with parameter
    Then response for delete should be no content

  Scenario: Test orders
    Given remote service instance
    Given user's body, name: "name", surname: "surname"
    When sending "POST" request to "users" with body
    Then response should be 201  with body not null
    Given order's body
    When sending "POST" request to "orders" with body
    Then response should be 201  with body not null
    When sending "PATCH" request to "orders" with parameter
    Then response should be 200  with body not null
    When sending "DELETE" request to "orders" with parameter
    Then response for delete should be no content
    When sending "DELETE" request to "users" with parameter
    Then response for delete should be no content


  Scenario: Test choice_products
    Given remote service instance
    Given inventory's body, name: "product", quantity: 200
    When sending "POST" request to "inventory" with body
    Then response should be 201  with body not null
    Given user's body, name: "name", surname: "surname"
    When sending "POST" request to "users" with body
    Then response should be 201  with body not null
    Given order's body
    When sending "POST" request to "orders" with body
    Then response should be 201  with body not null
    Given choice_product's body, quantity 100
    When sending "POST" request to "choice_products" with body
    Then response should be 201  with body not null
    Given choice_product's body, quantity 50
    When sending "PATCH" request to "choice_products" with body
    Then response should be 200  with body not null
    When sending "DELETE" request to "choice_products" with parameter
    Then response for delete should be no content
    When sending "DELETE" request to "inventory" with parameter
    Then response for delete should be no content
    Given order's body
    When sending "PATCH" request to "orders" with parameter
    Then response should be 200  with body not null
    When sending "DELETE" request to "orders" with parameter
    Then response for delete should be no content
    When sending "DELETE" request to "users" with parameter
    Then response for delete should be no content

Feature: abc
  
    Scenario Outline: GET to <string>
      Given remote service instance
      When sending "GET" request to <string>
      Then response should be  with body not null
      Examples:
        | string            | 
        | "products"        |
        | "inventory"       |
        | "users"           |
        | "orders"          |
        | "choice_products" |

      Scenario Outline: PATCH to <string1>
        Given remote service instance
        When sending "PATCH" request to <string1> with <string2>
        Then response should be  with body not null
        Examples:
          |string1              | string2                     |
          | "products/4"        |"{\"name\": \"ca\"}"          |
          | "inventory/2"       |"{\quantity\": 100}"         |
          | "users/4"           |"{\"surname\": \"kowalski"\}"|
          | "orders/9"          |""                           |
          | "choice_products/8" |"{\"quantity\": 100          |

    Scenario: POST and Delete
      Given remote service instance
      When sending "POST" request to "choice_products" with "{\"userId\" : 2 , \"productId\" : 3 , \"quantity\" : 2}"
      Then response for post should be ok with body not null
      When sending delete request to "choice_products"
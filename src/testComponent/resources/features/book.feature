Feature: the user can create and retrieve the books
  Scenario: user creates two books and retrieve both of them
    When the user creates the book "Les Misérables" written by "Victor Hugo"
    And the user creates the book "L'avare" written by "Molière"
    And the user get all books
    Then the list should contains the following books in the same order
      | id | author | title | reserved |
      | 2 | Molière | L'avare | false |
      | 1 | Victor Hugo | Les Misérables | false |

  Scenario: user reserves an available book
    Given the user creates the book "The Lord of the Rings" written by "J.R.R. Tolkien"
    When the user reserves the book with id 1
    And the user get the book with id 1
    Then the book should be reserved

  Scenario: user unreserve an reserved book
    Given the user creates the book "The Lord of the Rings" written by "J.R.R. Tolkien"
    When the user reserves the book with id 1
    And the user unreserves the book with id 1
    And the user get the book with id 1
    Then the book shouldn't be reserved

  Scenario: user try to reserve an unavailable book
    Given the user creates the book "Les Misérables" written by "Victor Hugo"
    When the user reserves the book with id 1
    Then the book with id 1 cannot be reserved : error 400

  Scenario: user try to reserve an unexistant book
      Then the book with id 17 cannot be reserved : error 404

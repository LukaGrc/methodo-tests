package com.example.demo

import io.cucumber.java.Before
import io.cucumber.java.PendingException
import io.cucumber.java.Scenario
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.path.json.JsonPath
import io.restassured.response.ValidatableResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.jdbc.core.JdbcTemplate

class BookStepDefs {
    @LocalServerPort
    private var port: Int? = 0

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Before
    fun setup(scenario: Scenario) {
        RestAssured.baseURI = "http://localhost:$port"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        jdbcTemplate.execute("TRUNCATE TABLE book RESTART IDENTITY CASCADE")
    }

    @When("the user creates the book {string} written by {string}")
    fun createBook(title: String, author: String) {
        given()
            .contentType(ContentType.JSON)
            .and()
            .body(
                """
                    {
                      "title": "$title",
                      "author": "$author"
                    }
                """.trimIndent()
            )
            .`when`()
            .post("/books")
            .then()
            .statusCode(201)
    }

    @When("the user get all books")
    fun getAllBooks() {
        lastBookResult = given()
            .`when`()
            .get("/books")
            .then()
            .statusCode(200)
    }

    @When("the user {word} the book with id {int}")
    fun actionBook(action: String, id: Int) {
        if (action == "get") {
            lastBookResult = given()
                .`when`()
                .get("/books/$id")
                .then()
            return
        }

        val reservationStatus = action == "reserves"
        given()
            .contentType(ContentType.JSON)
            .and()
            .body(reservationStatus.toString())
            .`when`()
            .patch("/books/$id/reservation")
            .then()
            .statusCode(200)
    }

    @Then("the list should contains the following books in the same order")
    fun shouldHaveListOfBooks(payload: List<Map<String, String>>) {
        val expectedResponse = payload.joinToString(separator = ",", prefix = "[", postfix = "]") { line ->
            """
                ${
                line.entries.joinToString(separator = ",", prefix = "{", postfix = "}") { entry ->
                    val key = entry.key
                    val value = entry.value
                    
                    when (key) {
                        "id" -> """"$key": $value"""
                        "reserved" -> """"$key": $value"""
                        else -> """"$key": "$value""""
                    }
                }
            }
            """.trimIndent()

        }
        lastBookResult.extract().body().jsonPath().prettify() shouldBe JsonPath(expectedResponse).prettify()
    }

    @Then("the book {word} be reserved")
    fun bookIsReserved(action: String? = null) {
        val expectedStatus = (action == "should")

        lastBookResult.extract().body().jsonPath().getBoolean("reserved") shouldBe expectedStatus
    }

    @Then("the book with id {int} cannot be reserved : error {int}")
    fun bookCannotBeReserved(id: Int, error: Int) {
        given()
            .contentType(ContentType.JSON)
            .and()
            .body("true")
            .`when`()
            .patch("/books/$id/reservation")
            .then()
            .statusCode(error)
    }

    companion object {
        lateinit var lastBookResult: ValidatableResponse
    }
}
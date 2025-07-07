package com.example.demo.domain.model

data class Book(
    val id: Int? = null,
    val author: String,
    val title: String,
    val isReserved: Boolean = false
) {
    init {
        require(author.isNotBlank()) { "Book author must not be blank!" }
        require(title.isNotBlank()) { "Book title must not be blank!" }
    }
}
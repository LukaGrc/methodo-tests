package com.example.demo.infrastructure.driving.dto

import com.example.demo.domain.model.Book

data class BookDTO(
    val author: String,
    val title: String
) {
    fun toDomain(): Book = Book(this.author, this.title)
}

fun Book.toDto(): BookDTO = BookDTO(this.author, this.title)
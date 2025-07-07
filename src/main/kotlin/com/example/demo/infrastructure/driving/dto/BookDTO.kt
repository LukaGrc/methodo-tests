package com.example.demo.infrastructure.driving.dto

import com.example.demo.domain.model.Book

data class BookDTO(
    val id: Int? = null,
    val author: String,
    val title: String,
    val isReserved: Boolean = false
) {
    fun toDomain(): Book = Book(this.id, this.author, this.title, this.isReserved)
}

fun Book.toDto(): BookDTO = BookDTO(this.id,this.author, this.title, this.isReserved)
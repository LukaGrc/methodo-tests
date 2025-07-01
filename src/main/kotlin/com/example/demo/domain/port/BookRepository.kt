package com.example.demo.domain.port

import com.example.demo.domain.model.Book

interface BookRepository {
    fun add(book: Book)
    fun getAll(): List<Book>
}
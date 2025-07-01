package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository

class BookListUseCase(val bookRepository: BookRepository) {
    fun getBooks() : List<Book> = bookRepository.getAll().sortedBy { it.title }
    fun addBook(book: Book) {
        bookRepository.add(book)
    }
}
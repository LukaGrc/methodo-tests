package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository

class BookListUseCase(val bookRepository: BookRepository) {
    fun getBooks() : List<Book> = bookRepository.getAll().sortedBy { it.title }
    fun addBook(book: Book) {
        bookRepository.add(book)
    }
    fun setBookReservation(book: Book, isReserved: Boolean) {
        if (book.isReserved == isReserved) throw IllegalArgumentException("The book is already " + (if (isReserved) "reserved" else "not reserved"))
        bookRepository.setBookReservation(book, isReserved)
    }
    fun getBook(id: Int): Book? = bookRepository.getAll().find { it.id == id }
}
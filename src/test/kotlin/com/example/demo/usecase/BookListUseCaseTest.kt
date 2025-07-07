package com.example.demo.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import com.example.demo.domain.usecase.BookListUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify

class BookListUseCaseTest: StringSpec ({
    val bookRepository = mockk<BookRepository>()
    val bookListUseCase = BookListUseCase(bookRepository)

    "getAll returns sorted list of books" {
        // Arrange
        val books = listOf(
            Book(author="Author1", title="Book1"),
            Book(author="Author3", title="Book3"),
            Book(author="Author2", title="Book2")
        )
        every { bookRepository.getAll() } returns books

        // Act
        val result = bookListUseCase.getBooks()

        // Assert
        result shouldContainExactly listOf(
            Book(author="Author1", title="Book1"),
            Book(author="Author2", title="Book2"),
            Book(author="Author3", title="Book3")
        )
    }

    "add method adds a book to the repository" {
        // Arrange
        val book = Book(author="NewAuthor", title="NewTitle")
        justRun { bookRepository.add(any()) }

        // Act
        bookListUseCase.addBook(book)

        // Assert
        verify(exactly = 1) { bookRepository.add(book) }
    }

    "setBookReservation method defines the book isReserved property" {
        // Arrange
        val book = Book(author="Author", title="Title", isReserved = false)
        justRun { bookRepository.setBookReservation(any(), any()) }

        // Act
        bookListUseCase.addBook(book)
        bookListUseCase.setBookReservation(book, true)

        // Assert
        verify(exactly = 1) { bookRepository.setBookReservation(book, true) }
    }
})
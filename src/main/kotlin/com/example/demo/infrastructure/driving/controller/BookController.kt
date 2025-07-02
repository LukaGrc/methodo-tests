package com.example.demo.infrastructure.driving.controller

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import com.example.demo.infrastructure.driving.dto.BookDTO
import com.example.demo.infrastructure.driving.dto.toDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController (private val bookRepository: BookRepository) {

    // GET localhost:8080/books
    @GetMapping
    fun getBooks() : List<BookDTO> {
        return bookRepository.getAll().map { it.toDto() }
    }

    // POST localhost:8080/books
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody book: BookDTO) {
        bookRepository.add(Book(book.author, book.title))
    }
}
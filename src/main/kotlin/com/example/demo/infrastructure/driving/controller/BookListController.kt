package com.example.demo.infrastructure.driving.controller

import com.example.demo.domain.usecase.BookListUseCase
import com.example.demo.infrastructure.driving.dto.BookDTO
import com.example.demo.infrastructure.driving.dto.toDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookListController(
    private val bookListUseCase: BookListUseCase
) {
    @CrossOrigin
    @GetMapping
    fun getBooks(): List<BookDTO> {
        return bookListUseCase.getBooks().map { it.toDto() }
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody bookDTO: BookDTO) {
        bookListUseCase.addBook(bookDTO.toDomain())
    }

    @CrossOrigin
    @PatchMapping("/{id}/reservation")
    @ResponseStatus(HttpStatus.OK)
    fun setBookReservationStatus(@PathVariable id: Int, @RequestBody status: Boolean): ResponseEntity<String?> {
        val book = bookListUseCase.getBook(id)
        if (book == null) {
            return ResponseEntity("The book does not exist.", HttpStatus.NOT_FOUND)
        }

        try {
            bookListUseCase.setBookReservation(book, status)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity.ok(null)
    }
}
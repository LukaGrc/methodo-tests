package com.example.demo.infrastructure.driving.controller

import com.example.demo.domain.usecase.BookListUseCase
import com.example.demo.infrastructure.driving.dto.BookDTO
import com.example.demo.infrastructure.driving.dto.toDto
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
    fun addBook(@RequestBody bookDTO: BookDTO) {
        bookListUseCase.addBook(bookDTO.toDomain())
    }
}
package com.example.demo.infrastructure.application

import com.example.demo.domain.port.BookRepository
import com.example.demo.domain.usecase.BookListUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCasesConfiguration {
    @Bean
    fun bookListUseCase(bookDAO: BookRepository): BookListUseCase {
        return BookListUseCase(bookDAO)
    }
}
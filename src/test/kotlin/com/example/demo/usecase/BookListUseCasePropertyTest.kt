package com.example.demo.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import com.example.demo.domain.usecase.BookListUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll

class InMemoryBookRepository: BookRepository {
    private val books = mutableListOf<Book>()

    override fun getAll(): List<Book> = books

    override fun add(book: Book) {
        books.add(book)
    }

    fun clear() {
        books.clear()
    }
}

class BookListUseCasePropertyTest : StringSpec({
    val bookRepository = InMemoryBookRepository()
    val bookListUseCase = BookListUseCase(bookRepository)

    "sould return all elements in the alphabetical order" {
        bookRepository.clear()
        val titles = mutableListOf<String>()

        checkAll(Arb.stringPattern("[A-Za-z]+")) { title ->
            bookRepository.add(Book("Author", title))
            titles.add(title)
        }

        bookListUseCase.getBooks().map { it.title } shouldBe titles.sorted()
    }
})
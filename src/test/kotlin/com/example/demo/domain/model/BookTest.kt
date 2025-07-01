package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BookTest : StringSpec({
    "A book needs an author" {
        shouldThrow<IllegalArgumentException> { Book( "", "Title") }
    }

    "A book needs a title" {
        shouldThrow<IllegalArgumentException> { Book( "Author", "") }
    }
})
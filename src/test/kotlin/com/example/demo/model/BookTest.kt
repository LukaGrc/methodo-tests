package com.example.demo.model

import com.example.demo.domain.model.Book
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BookTest : StringSpec({
    "A book needs an author" {
        shouldThrow<IllegalArgumentException> { Book( author="", title="Title") }
    }

    "A book needs a title" {
        shouldThrow<IllegalArgumentException> { Book( author="Author", title="") }
    }

    "A book has its property isReserved to false by default" {
        Book(author="Author", title="Title").isReserved shouldBe false
    }
})
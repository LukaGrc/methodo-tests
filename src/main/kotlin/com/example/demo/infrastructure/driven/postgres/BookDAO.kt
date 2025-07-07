package com.example.demo.infrastructure.driven.postgres

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class BookDAO(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate): BookRepository {
    override fun getAll(): List<Book> {
        return namedParameterJdbcTemplate
            .query("SELECT * FROM BOOK", MapSqlParameterSource()) { rs, _ ->
                Book(
                    id = rs.getInt("id"),
                    title = rs.getString("title"),
                    author = rs.getString("author"),
                    isReserved = rs.getBoolean("isReserved")
                )
            }
    }

    override fun add(book: Book) {
        namedParameterJdbcTemplate
            .update("INSERT INTO BOOK (title, author) values (:title, :author)", mapOf(
                "title" to book.title,
                "author" to book.author
            ))
    }

    override fun setBookReservation(book: Book, status: Boolean) {
        namedParameterJdbcTemplate
            .update("UPDATE BOOK SET \"isReserved\" = :isReserved WHERE id = :id", mapOf(
                "isReserved" to status,
                "id" to book.id,
            ))
    }

    override fun getBook(id: Int): Book? {
        return namedParameterJdbcTemplate
            .query("SELECT * FROM BOOK WHERE id = :id", mapOf("id" to id)) { rs, _ ->
                Book(
                    id = rs.getInt("id"),
                    title = rs.getString("title"),
                    author = rs.getString("author"),
                    isReserved = rs.getBoolean("isReserved")
                )
            }.firstOrNull()
    }
}
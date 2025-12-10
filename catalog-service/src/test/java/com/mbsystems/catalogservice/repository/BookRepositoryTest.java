package com.mbsystems.catalogservice.repository;

import com.mbsystems.catalogservice.config.DataConfig;
import com.mbsystems.catalogservice.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import({DataConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234561237";
        var book = Book.of(bookIsbn, "Title", "Author", 12.90, "Manning");

        this.jdbcAggregateTemplate.insert(book);

        Optional<Book> foundBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().isbn()).isEqualTo(book.isbn());
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        Optional<Book> actualBook = bookRepository.findByIsbn("1234561277");
        assertThat(actualBook).isEmpty();
    }

    @Test
    void existsByIsbnWhenExisting() {
        var bookIsbn = "1234571239";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90, "Manning");
        jdbcAggregateTemplate.insert(bookToCreate);

        boolean existing = bookRepository.existsByIsbn(bookIsbn);

        assertThat(existing).isTrue();
    }

    @Test
    void existsByIsbnWhenNotExisting() {
        boolean existing = bookRepository.existsByIsbn("1234561240");
        assertThat(existing).isFalse();
    }

    @Test
    void deleteByIsbn() {
        var bookIsbn = "1234561241";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90, "Manning");
        var persistedBook = jdbcAggregateTemplate.insert(bookToCreate);

        bookRepository.deleteByIsbn(bookIsbn);

        assertThat(jdbcAggregateTemplate.findById(persistedBook.id(), Book.class)).isNull();
    }
}
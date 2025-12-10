package com.mbsystems.catalogservice.demo;

import com.mbsystems.catalogservice.entity.Book;
import com.mbsystems.catalogservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata")
@RequiredArgsConstructor
public class BookDataLoader {

    private final BookRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        this.bookRepository.deleteAll();

        var book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "Manning");

        var book2 = Book.of("1234567892", "The Hobbit", "Iorek Polarson", 12.99, "Manning");

        this.bookRepository.saveAll(List.of(book1, book2));
    }
}

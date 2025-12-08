package com.mbsystems.catalogservice.demo;

import com.mbsystems.catalogservice.entity.Book;
import com.mbsystems.catalogservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
@RequiredArgsConstructor
public class BookDataLoader {

    private final BookRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = new Book("1234567891", "Northern Lights", "Lyra Silverstar", 9.90);

        var book2 = new Book("1234567892", "The Hobbit", "Iorek Polarson", 12.99);

        this.bookRepository.save(book1);
        this.bookRepository.save(book2);
    }
}

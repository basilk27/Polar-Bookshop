package com.mbsystems.catalogservice.controller;

import com.mbsystems.catalogservice.entity.Book;
import com.mbsystems.catalogservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Iterable<Book> get() {
        return this.bookService.viewBookList();
    }

    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return this.bookService.viewBookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book) {
        return this.bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        this.bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}")
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
        return this.bookService.editBookDetails(isbn, book);
    }
}

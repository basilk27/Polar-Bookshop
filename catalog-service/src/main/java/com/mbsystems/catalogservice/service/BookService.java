package com.mbsystems.catalogservice.service;

import com.mbsystems.catalogservice.entity.Book;
import com.mbsystems.catalogservice.exception.BookAlreadyExistsException;
import com.mbsystems.catalogservice.exception.BookNotFoundException;
import com.mbsystems.catalogservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Iterable<Book> viewBookList() {
        return this.bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return this.bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (this.bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }

        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        this.bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        return this.bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.id(),
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price(),
                            existingBook.publisher(),
                            existingBook.createdDate(),
                            existingBook.lastModifiedDate(),
                            existingBook.version());
                    return this.bookRepository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));
    }
}

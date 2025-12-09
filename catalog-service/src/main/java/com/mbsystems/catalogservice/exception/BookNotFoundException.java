package com.mbsystems.catalogservice.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String isbn) {
        super("The book with ISBN " + isbn + " was not found, please check the ISBN and try again.");
    }
}

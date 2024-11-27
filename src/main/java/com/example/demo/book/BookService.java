package com.example.demo.book;

import com.example.demo.utils.exception.ApiRequestException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getBooks();

    Optional<Book> getBook(Long id);

    Book createBook(BookReq req) throws ApiRequestException;

    Book updateBookIsAvailable(Long id, Boolean isAvailable) throws ApiRequestException;

    void deleteBook(Long id) throws ApiRequestException;
}

package com.example.demo.book;

import com.example.demo.utils.exception.ApiRequestException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBook(Long id) throws ApiRequestException {
        return bookRepository.findById(id);
    }

    @Override
    public Book createBook(BookReq req) throws ApiRequestException {
        Optional<Book> book = bookRepository.findByTitle(req.title());
        if (book.isPresent()) {
            throw new ApiRequestException("Book is present!");
        }
        return bookRepository.save(new Book(req.title(), req.author(), req.category()));
    }

    @Override
    public Book updateBookIsAvailable(Long id, Boolean isAvailable) throws ApiRequestException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new ApiRequestException("Book not found!");
        }
        if (book.get().getIsAvailable().equals(isAvailable)) {
            throw new ApiRequestException("Book status already has this value!");
        }

        book.get().setIsAvailable(isAvailable);
        book.get().setUpdatedAt(new Date());
        return bookRepository.save(book.get());
    }

    @Override
    public void deleteBook(Long id) throws ApiRequestException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new ApiRequestException("Book not found!");
        }
        bookRepository.delete(book.get());
    }
}

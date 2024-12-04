package com.example.demo.book;

import com.example.demo.utils.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<List<Book>> getBooks() throws ApiRequestException {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable Long id) throws ApiRequestException {
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> createBook(@RequestBody BookReq req) throws ApiRequestException {
        return new ResponseEntity<>(bookService.createBook(req), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBookIsAvailable(
            @PathVariable Long id,
            @RequestBody Boolean isAvailable) throws ApiRequestException {
        return new ResponseEntity<>(bookService.updateBookIsAvailable(id, isAvailable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) throws ApiRequestException {
        bookService.deleteBook(id);
        return new ResponseEntity<>("Delete author successfully", HttpStatus.OK);
    }
}

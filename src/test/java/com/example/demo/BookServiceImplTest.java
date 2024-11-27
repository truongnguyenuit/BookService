package com.example.demo;

import com.example.demo.book.Book;
import com.example.demo.book.BookRepository;
import com.example.demo.book.BookReq;
import com.example.demo.book.BookServiceImpl;
import com.example.demo.utils.exception.ApiRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void shouldCreateBookSuccessfully() {
        // Writing mock for findByTitle function
        when(bookRepository
                .findByTitle("Title"))
                .thenReturn(Optional.empty());

        // Writing mock for save function
        when(bookRepository
                .save(any(Book.class)))
                .thenReturn(new Book("Title", "Author", "Category"));

        Book createdBook = bookService.
                createBook(new BookReq("Title", "Author", "Category"));

        //Result checking
        assertNotNull(createdBook);
        assertEquals("Title", createdBook.getTitle());
        assertEquals("Author", createdBook.getAuthor());
        assertEquals("Category", createdBook.getCategory());

        //verify contact with repository
        verify(bookRepository, times(1)).findByTitle("Title");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenBookAlreadyExists() {
        BookReq req = new BookReq("Title", "Author", "Category");

        when(bookRepository
                .findByTitle(req.title()))
                .thenReturn(Optional.of(new Book()));

        // Verify throwing exception
        assertThrows(ApiRequestException.class, () -> bookService.createBook(req));

        // Verify not call save function
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldGetBooksSuccessfully() {
        when(bookRepository.findAll())
                .thenReturn(List.of(new Book(
                        1l,
                        "Title",
                        "Author",
                        "Category",
                        true)));

        List<Book> books = bookService.getBooks();

        assertNotNull(books);
        assertEquals(1l, books.get(0).getId());
        assertEquals("Title", books.get(0).getTitle());
        assertEquals("Author", books.get(0).getAuthor());
        assertEquals("Category", books.get(0).getCategory());
        assertEquals(true, books.get(0).getIsAvailable());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldGetBookSuccessfully() {
        when(bookRepository.findById(1l))
                .thenReturn(Optional.of(new Book(
                        1l,
                        "Title",
                        "Author",
                        "Category",
                        true)));

        Optional<Book> book = bookService.getBook(1l);

        assertNotNull(book);
        assertEquals(1l, book.get().getId());
        assertEquals("Title", book.get().getTitle());
        assertEquals("Author", book.get().getAuthor());
        assertEquals("Category", book.get().getCategory());
        assertEquals(true, book.get().getIsAvailable());

        verify(bookRepository, times(1)).findById(1l);
    }

    @Test
    void shouldUpdateStatusBookSuccessfully() {
        when(bookRepository.findById(1l))
                .thenReturn(Optional.of(new Book(
                        1l,
                        "Title",
                        "Author",
                        "Category",
                        true)));

        when(bookRepository.save(any(Book.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Book updateBook = bookService.updateBookIsAvailable(1l, false);

        assertNotNull(updateBook);
        assertEquals(updateBook.getIsAvailable(), false);

        verify(bookRepository, times(1)).findById(1l);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldThrowExceptionIfBookNotFound() {
        when(bookRepository.findById(1l)).thenReturn(Optional.empty());

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> bookService.updateBookIsAvailable(1l, true));

        assertEquals("Book not found!", exception.getMessage());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldThrowExceptionIfBookAlreadyHasThisValue() {
        when(bookRepository.findById(1l)).thenReturn(Optional.of(new Book(
                1l,
                "Title",
                "Author",
                "Category",
                true)));

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> bookService.updateBookIsAvailable(1l, true));

        assertEquals("Book status already has this value!", exception.getMessage());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenDeleteBookFail() {
        when(bookRepository.findById(1l))
                .thenReturn(Optional.empty());

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> bookService.deleteBook(1l));

        assertEquals("Book not found!", exception.getMessage());
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void shouldDeleteBookSuccessfully() {
        when(bookRepository.findById(1l))
                .thenReturn(Optional.of(new Book(
                1l,
                "Title",
                "Author",
                "Category",
                true)));

        bookService.deleteBook(1l);
        verify(bookRepository, times(1)).delete(any(Book.class));
    }
}

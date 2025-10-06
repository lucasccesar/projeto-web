package br.com.bookly.controllers;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Users;
import br.com.bookly.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooks(pageable));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        Book addedBook = bookService.addBook(book);

        if (addedBook != null) {
            return ResponseEntity.status(201).body(addedBook);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book book) {
        Book updated = bookService.updateBook(id, book);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/increase")
    public ResponseEntity<Book> increaseAvailableBooks(@PathVariable UUID id, @RequestParam int amount) {
        Book updatedBook = bookService.increaseAvaliableBooks(id, amount);
        if (updatedBook != null) {
            return ResponseEntity.status(201).body(updatedBook);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/decrease")
    public ResponseEntity<Book> decreaseAvailableBooks(@PathVariable UUID id, @RequestParam int amount) {
        Book updatedBook = bookService.decreaseAvaliableBooks(id, amount);
        if (updatedBook != null) {
            return ResponseEntity.status(201).body(updatedBook);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = "title")
    public ResponseEntity<Page<Book>> getBooksByTitle(Pageable pageable, @RequestParam String title) {
        return ResponseEntity.ok(bookService.getBooksByTitleContaining(pageable, title));
    }

    @GetMapping(params = "genre")
    public ResponseEntity<Page<Book>> getBooksByGenre(Pageable pageable, @RequestParam String genre) {
        return ResponseEntity.ok(bookService.getBooksByGenre(pageable, genre));
    }

    @GetMapping("/availables")
    public ResponseEntity<Page<Book>> getAvailableBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAvailableBooks(pageable));
    }

    @GetMapping("/unavailables")
    public ResponseEntity<Page<Book>> getUnavailableBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getUnavailableBooks(pageable));
    }
}

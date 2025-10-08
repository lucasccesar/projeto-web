package br.com.bookly.controllers;

import br.com.bookly.entities.Author;
import br.com.bookly.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
   AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author created = authorService.createAuthor(author);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(created);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable UUID id) {
        boolean deleted = authorService.deleteAuthorById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody Author author) {
        Author updated = authorService.updateAuthor(id, author);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findAuthorById(@PathVariable UUID id) {
        Author author = authorService.findAuthorById(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @GetMapping("/name")
    public ResponseEntity<Page<Author>> findAuthorByName(@RequestParam String name, Pageable pageable) {
        Page<Author> author = authorService.findAuthorByName(name, pageable);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @GetMapping
    public ResponseEntity<Page<Author>> listAllAuthors(Pageable pageable) {
        return ResponseEntity.ok(authorService.findAllAuthors(pageable));
    }
}
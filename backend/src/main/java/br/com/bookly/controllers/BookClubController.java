package br.com.bookly.controllers;


import br.com.bookly.entities.BookClub;
import br.com.bookly.services.BookClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bookclub")
public class BookClubController {

    @Autowired
    BookClubService bookClubService;

    @GetMapping
    public ResponseEntity<Page<BookClub>> listBookClubs(Pageable pageable) {
        return ResponseEntity.ok(bookClubService.listBookClub(pageable));
    }

    // todo: 2 gets

    @PostMapping
    public ResponseEntity<BookClub> createBookClub(@RequestBody BookClub bookClub) {
        BookClub createdBookClub = bookClubService.createBookClub(bookClub);

        if (createdBookClub != null) {
            return ResponseEntity.status(201).body(createdBookClub);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookClub> updateBookClub(@PathVariable UUID id, @RequestBody BookClub bookClub) {
        BookClub updated = bookClubService.updateBookClub(id, bookClub);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<BookClub> deleteBookClub(@RequestBody BookClub bookClub) {
        boolean deleted = bookClubService.deleteBookClub(bookClub);
        if (deleted) {
            return ResponseEntity.ok(bookClub);//vai retornar o objeto deletado
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookClubById(@PathVariable UUID id) {
        boolean deleted = bookClubService.deleteBookClubById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}

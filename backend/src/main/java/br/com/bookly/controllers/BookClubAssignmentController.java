package br.com.bookly.controllers;

import br.com.bookly.entities.BookClubAssignment;
import br.com.bookly.services.BookClubAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bookclubassignment")
public class BookClubAssignmentController {

    @Autowired
    BookClubAssignmentService bookClubAssignmentService;

    @GetMapping
    public ResponseEntity<Page<BookClubAssignment>> listBookClubAssignments(Pageable pageable) {
        return ResponseEntity.ok(bookClubAssignmentService.findAllBookClubsAssignment(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookClubAssignment> findBookClubAssignmentById(@PathVariable UUID id) {
        BookClubAssignment bookClubAssignment = bookClubAssignmentService.findBookClubAssignmentById(id);

        if (bookClubAssignment != null) {
            return ResponseEntity.ok(bookClubAssignment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BookClubAssignment> createBookClubAssignment(@RequestBody BookClubAssignment bookClubAssignment) {
        BookClubAssignment createdBookClubAssignment = bookClubAssignmentService.createBookClubAssignment(bookClubAssignment);

        if (createdBookClubAssignment != null) {
            return ResponseEntity.status(201).body(createdBookClubAssignment);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookClubAssignment> updateBookClubAssignment(@PathVariable UUID id, @RequestBody BookClubAssignment bookClubAssignment) {
        BookClubAssignment updated = bookClubAssignmentService.updateBookClubAssignment(id, bookClubAssignment);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updated);
        }
    }

        @DeleteMapping
    public ResponseEntity<BookClubAssignment> deleteBookClubAssignment(@RequestBody BookClubAssignment bookClubAssignment) {
        boolean deleted = bookClubAssignmentService.deleteBookClubAssignment(bookClubAssignment);

        if(deleted) {
            return ResponseEntity.ok(bookClubAssignment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookClubAssignmentById(@PathVariable UUID id) {
        boolean deleted = bookClubAssignmentService.deleteById(id);

        if(deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

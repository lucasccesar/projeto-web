package br.com.bookly.controllers;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Rating;
import br.com.bookly.entities.Users;
import br.com.bookly.entities.dtos.RatingDTO;
import br.com.bookly.entities.dtos.UsersDTO;
import br.com.bookly.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<RatingDTO>> getRatings(Pageable pageable) {
        Page<RatingDTO> ratings = ratingService.getAllRatings(pageable).map(RatingDTO::new);
        return ResponseEntity.ok().body(ratings);
    }

    @PostMapping
    public ResponseEntity<RatingDTO> addRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.addRating(rating);
        if(savedRating == null) {
            return ResponseEntity.badRequest().build();
        }
        RatingDTO ratingDTO = new RatingDTO(savedRating);
        return ResponseEntity.ok().body(ratingDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable UUID id, @RequestBody Rating rating) {
        Rating updated = ratingService.updateRating(id, rating);
        if (updated != null) {
            RatingDTO ratingDTO = new RatingDTO(updated);
            return ResponseEntity.ok(ratingDTO);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable UUID id) {
        boolean deleted = ratingService.deleteRating(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<Page<RatingDTO>> getRatingsByBook(@PathVariable UUID id, Pageable pageable) {
        Page<RatingDTO> ratings = ratingService.getRatingsByBookId(id, pageable).map(RatingDTO::new);
        return ResponseEntity.ok().body(ratings);
    }

    @GetMapping("/{id}/average")
    public ResponseEntity<Double> getAverageRatingByBookId(@PathVariable UUID id) {
        Double avg = ratingService.getAverageRatingByBookId(id);
        if(avg == -1){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(avg);
    }
}

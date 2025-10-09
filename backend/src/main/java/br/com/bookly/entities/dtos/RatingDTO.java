package br.com.bookly.entities.dtos;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Rating;
import br.com.bookly.entities.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO {

    private UUID id;
    private UsersDTO user;
    private BookDTO book;
    private String comment;
    private int ratingValue;
    private LocalDate ratingDate;

    public RatingDTO(UUID id, Users user, Book book, String comment, int ratingValue, LocalDate ratingDate) {
        this.id = id;
        this.user = new UsersDTO(user);
        this.book = new BookDTO(book);
        this.comment = comment;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
    }

    public RatingDTO(Rating rating){
        this.id = rating.getIdRating();
        this.user = new UsersDTO(rating.getUser());
        this.book = new BookDTO(rating.getBook());
        this.comment = rating.getComment();
        this.ratingValue = rating.getRatingValue();
        this.ratingDate = rating.getRatingDate();

    }

}

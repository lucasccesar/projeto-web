package br.com.bookly.services.impl;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Rating;
import br.com.bookly.entities.Users;
import br.com.bookly.repositories.BookRepository;
import br.com.bookly.repositories.RatingRepository;
import br.com.bookly.repositories.UsersRepository;
import br.com.bookly.services.RatingService;
import br.com.bookly.services.UsersService;
import br.com.bookly.services.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    bookService bookService;

    @Override
    public Page<Rating> getAllRatings(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }

    @Override
    public Rating addRating(Rating rating) {
        if (rating.getUser() == null || rating.getBook() == null) {
            return null;
        }

        if (rating.getUser().getId() == null || rating.getBook().getIdBook() == null) {
            return null;
        }

        if (rating.getRatingValue() < 0 || rating.getRatingValue() > 10) {
            return null;
        }

        if (!ratingRepository.findByUserIdAndBook_IdBook(rating.getUser().getId(), rating.getBook().getIdBook()).isEmpty()) {
            return null;
        }

        Optional<Users> optionalUser = usersService.getUsersRepository().findById(rating.getUser().getId());
        Optional<Book> optionalBook = bookService.getBookRepository().findById(rating.getBook().getIdBook());

        if (optionalUser.isEmpty() || optionalBook.isEmpty()) {
            return null;
        }

        rating.setUser(optionalUser.get());
        rating.setBook(optionalBook.get());
        rating.setRatingDate(LocalDate.now());

        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(UUID id, Rating rating) {
        if(rating == null || id == null){
            return null;
        }

        Rating ratingDB = ratingRepository.findById(id).orElse(null);

        if(ratingDB == null){
            return null;
        }

        if (!ratingDB.getBook().getIdBook().equals(rating.getBook().getIdBook()) ||
                !ratingDB.getUser().getId().equals(rating.getUser().getId())) {
            return null;
        }

        ratingDB.setComment(rating.getComment());

        if(rating.getRatingValue() < 0 || rating.getRatingValue() > 10){
            return null;
        }

        ratingDB.setRatingValue(rating.getRatingValue());
        ratingDB.setRatingDate(LocalDate.now());

        return ratingRepository.save(ratingDB);
    }

    @Override
    public boolean deleteRating(UUID id) {
        if(!ratingRepository.existsById(id)){
            return false;
        }
        ratingRepository.deleteById(id);
        if(!ratingRepository.existsById(id)){
            return true;
        }
        return false;
    }

    @Override
    public Page<Rating> getRatingsByBookId(UUID bookId, Pageable pageable) {
        return ratingRepository.findByBook_IdBook(bookId, pageable);
    }

    @Override
    public double getAverageRatingByBookId(UUID bookId) {
        double avg = 0;
        List<Rating> ratings = ratingRepository.findByBook_IdBook(bookId);
        if(bookId == null){
            throw new BadRequestException("Error: Book ID must not be null");
        }

        if(ratings == null){
            throw new BadRequestException("Error: Ratings must not be null");
        }

        for(Rating rating : ratings){
            avg += rating.getRatingValue();
        }
        return avg/ratings.size();
    }
}

package br.com.bookly.services.impl;

import br.com.bookly.entities.Book;
import br.com.bookly.repositories.BookRepository;
import br.com.bookly.services.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BookServiceImpl implements bookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    @Override
    public Book addBook(Book book) {

        if(book.getTitle() == null || book.getTitle().isBlank()) {
            return null;
        }

        if(book.getSynopsis() == null || book.getSynopsis().isBlank()) {
            return null;
        }

        if(book.getGenre() == null || book.getGenre().isBlank()) {
            return null;
        }

        if(book.getAvailableQuantity() < 0) {
            return null;
        }

        if(book.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return bookRepository.save(book);

    }

    @Override
    public Book updateBook(UUID id, Book book) {

        if(book == null || id == null){
            return null;
        }

        Book bookById = bookRepository.findById(id).orElse(null);

        if(bookById == null) {
            return null;
        }

        bookById.setTitle(book.getTitle());
        bookById.setSynopsis(book.getSynopsis());
        bookById.setGenre(book.getGenre());
        bookById.setPrice(book.getPrice());

        return bookRepository.save(bookById);

    }

    @Override
    public boolean deleteBook(UUID id) {
        bookRepository.deleteById(id);

        if(!bookRepository.existsById(id)){
            return true;
        }

        return false;
    }

    @Override
    public Book increaseAvaliableBooks(UUID id, int amount) {

        if(id == null || amount <= 0){
            return null;
        }

        Book bookById = bookRepository.findById(id).orElse(null);

        if(bookById == null) {
            return null;
        }

        bookById.setAvailableQuantity(bookById.getAvailableQuantity() + amount);
        bookRepository.save(bookById);

        return bookById;
    }

    @Override
    public Book decreaseAvaliableBooks(UUID id, int amount) {
        if(id == null){
            return null;
        }

        Book bookById = bookRepository.findById(id).orElse(null);

        if(bookById == null) {
            return null;
        }

        if(bookById.getAvailableQuantity() < amount){
            return null;
        }

        bookById.setAvailableQuantity(bookById.getAvailableQuantity() - amount);
        bookRepository.save(bookById);

        return bookById;
    }

    @Override
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> getBooksByTitleContaining(Pageable pageable, String title) {
        return bookRepository.findByTitleContaining(pageable, title);
    }

    @Override
    public Page<Book> getBooksByGenre(Pageable pageable, String genre) {
        return bookRepository.findByGenre(pageable, genre);
    }

    @Override
    public Page<Book> getAvailableBooks(Pageable pageable) {
        return bookRepository.findAvailable(pageable);
    }

    @Override
    public Page<Book> getUnavailableBooks(Pageable pageable) {
        return bookRepository.findUnavailable(pageable);
    }
}

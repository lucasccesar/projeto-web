package br.com.bookly.services;

import br.com.bookly.entities.Author;
import br.com.bookly.entities.Book;
import br.com.bookly.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookService {

    Book addBook(Book book);
    Book updateBook(UUID id, Book book);
    boolean deleteBook(UUID id);
    Book increaseAvaliableBooks(UUID id, int amount);
    Book decreaseAvaliableBooks(UUID id, int amount);
    Page<Book> getBooks(Pageable pageable);
    Page<Book> getBooksByTitleContaining(Pageable pageable, String title);
    //Page<Book> getBooksByAuthor(Pageable pageable, Author author);
    Page<Book> getBooksByGenre(Pageable pageable, String genre);
    Page<Book> getAvailableBooks(Pageable pageable);
    Page<Book> getUnavailableBooks(Pageable pageable);

}

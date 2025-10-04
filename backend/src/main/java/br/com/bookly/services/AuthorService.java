package br.com.bookly.services;

import br.com.bookly.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuthorService {

    public Author createAuthor(Author author);
    public boolean deleteAuthor(Author author);
    public boolean deleteAuthorById(UUID idAuthor);
    public Author updateAuthor(UUID idAuthor, Author author);
    public Author findAuthorById(UUID idAuthor);
    public Author findAuthorByName(String name);
    public Page<Author> findAllAuthors(Pageable pageable);

}
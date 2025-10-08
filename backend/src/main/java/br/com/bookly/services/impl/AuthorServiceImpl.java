package br.com.bookly.services.impl;

import br.com.bookly.entities.Author;
import br.com.bookly.repositories.AuthorRepository;
import br.com.bookly.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author author) {
        if(author.getName() == null || author.getName().isBlank())
            return null;

        return authorRepository.save(author);
    }

    @Override
    public boolean deleteAuthor(Author author) {

        Author exists = authorRepository.findById(author.getIdAuthor()).orElse(null);

        if(exists == null)
            return false;

        authorRepository.delete(exists);
        return true;
    }

    @Override
    public boolean deleteAuthorById(UUID idAuthor) {
        if(authorRepository.existsById(idAuthor) == false)
            return false;

        authorRepository.deleteById(idAuthor);
        return true;

    }

    @Override
    public Author updateAuthor(UUID idAuthor, Author author) {
        Author exists = authorRepository.findById(idAuthor).orElse(null);

        if(exists == null)
            return null;

        if(author.getName() == null || author.getName().isBlank())
            return null;

        exists.setName(author.getName());
        return authorRepository.save(exists);
    }

    @Override
    public Author findAuthorById(UUID idAuthor) {
        return authorRepository.findById(idAuthor).orElse(null);
    }

    @Override
    public Page<Author> findAuthorByName(String name, Pageable pageable) {
        return authorRepository.findAuthorByNameIgnoreCase(name, pageable);
    }

    @Override
    public Page<Author> findAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }
}

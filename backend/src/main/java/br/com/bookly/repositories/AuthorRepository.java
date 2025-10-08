package br.com.bookly.repositories;

import br.com.bookly.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Page<Author> findAuthorByNameIgnoreCase(String name, Pageable pageable);
}

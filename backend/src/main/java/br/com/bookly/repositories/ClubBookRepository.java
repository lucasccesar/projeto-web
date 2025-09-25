package br.com.bookly.repositories;

import br.com.bookly.entities.ClubBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClubBookRepository extends JpaRepository<ClubBook, UUID> {
}

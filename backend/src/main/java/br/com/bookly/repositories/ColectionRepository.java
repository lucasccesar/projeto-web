package br.com.bookly.repositories;

import br.com.bookly.entities.Colection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColectionRepository extends JpaRepository<Colection, UUID> {
    boolean existsByNameIgnoreCase(String name);
    Colection findByNameIgnoreCase(String name);
}

package br.com.bookly.repositories;

import br.com.bookly.entities.Colection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColectionRepository extends JpaRepository<Colection, UUID> {
    boolean existsByNameIgnoreCase(String name);
    Colection findByNameIgnoreCase(String name);
    List<Colection> findByUser_IdUser(UUID userId);
    boolean existsByNameIgnoreCaseAndUser_IdUser(String name, UUID userId);
}

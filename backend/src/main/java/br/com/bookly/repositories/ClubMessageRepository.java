package br.com.bookly.repositories;

import br.com.bookly.entities.ClubMessage;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClubMessageRepository extends JpaRepository<ClubMessage, UUID> {
}

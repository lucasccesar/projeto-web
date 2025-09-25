package br.com.bookly.repositories;

import br.com.bookly.entities.ParticipantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipantUserRepository extends JpaRepository<ParticipantUser, UUID> {
}

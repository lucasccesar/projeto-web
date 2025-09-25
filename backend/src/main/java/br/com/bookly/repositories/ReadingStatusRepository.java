package br.com.bookly.repositories;

import br.com.bookly.entities.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReadingStatusRepository extends JpaRepository<ReadingStatus, UUID> {
}

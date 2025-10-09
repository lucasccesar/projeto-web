package br.com.bookly.repositories;

import br.com.bookly.entities.ReadingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReadingStatusRepository extends JpaRepository<ReadingStatus, UUID> {
    public ReadingStatus findByBook_IdBook(UUID idBook);
    public Page<ReadingStatus> findByUsers_id(UUID userId, Pageable pageable);
}

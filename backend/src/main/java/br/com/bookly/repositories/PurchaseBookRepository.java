package br.com.bookly.repositories;

import br.com.bookly.entities.PurchaseBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseBookRepository extends JpaRepository<PurchaseBook, UUID> {
}

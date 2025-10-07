package br.com.bookly.repositories;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.PurchaseBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseBookRepository extends JpaRepository<PurchaseBook, UUID> {
    public PurchaseBook findPurchaseBookByPurchase_IdPurchase(UUID idPurchase);
    public PurchaseBook findPurchaseBookByBook(Book book);
    public Page<PurchaseBook> findByPurchase(UUID idPurchase, Pageable pageable);
}

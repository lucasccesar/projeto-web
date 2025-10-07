package br.com.bookly.services;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Purchase;
import br.com.bookly.entities.PurchaseBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.PublicKey;
import java.util.UUID;

public interface PurchaseBookService {

    public PurchaseBook createPurchaseBook(PurchaseBook purchaseBook);
    public boolean deletePurchaseBook(UUID id);
    public PurchaseBook updatePurchaseBook(UUID id, PurchaseBook purchaseBook);
    public PurchaseBook findPurchaseBookById(UUID id);
    public PurchaseBook findPurchaseBookByPurchase_IdPurchase(UUID idPurchase);
    public PurchaseBook findPurchaseBookByBook(Book book);
    public Page<PurchaseBook> findByPurchase(UUID idPurchase, Pageable pageable);
    public Page<PurchaseBook> findAllPurchaseBooks(Pageable pageable);


}

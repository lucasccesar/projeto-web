package br.com.bookly.services.impl;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Purchase;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.repositories.PurchaseBookRepository;
import br.com.bookly.services.PurchaseBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseBookServiceImpl implements PurchaseBookService {

    @Autowired
    PurchaseBookRepository purchaseBookRepository;


    @Override
    public PurchaseBook createPurchaseBook(PurchaseBook purchaseBook) {
        if(purchaseBook.getQuantity() == null){
            return null;
        }

        if(purchaseBook.getBook() == null){
            return null;
        }

        return purchaseBookRepository.save(purchaseBook);
    }

    @Override
    public boolean deletePurchaseBook(UUID id) {

        PurchaseBook exists = purchaseBookRepository.findById(id).orElse(null);
        if(exists == null){
            return false;
        }

        purchaseBookRepository.delete(exists);
        return true;
    }

    @Override
    public PurchaseBook updatePurchaseBook(UUID id, PurchaseBook purchaseBook) {
        PurchaseBook exists = purchaseBookRepository.findById(id).orElse(null);
        if(exists == null){
            return null;
        }

        if(purchaseBook.getQuantity() == null){
            return null;
        }

        if(purchaseBook.getBook() == null){
            return null;
        }

        exists.setQuantity(purchaseBook.getQuantity());
        exists.setBook(purchaseBook.getBook());

        return purchaseBookRepository.save(exists);
    }

    @Override
    public PurchaseBook findPurchaseBookById(UUID id) {
        return purchaseBookRepository.findById(id).orElse(null);
    }

    @Override
    public PurchaseBook findPurchaseBookByPurchase_IdPurchase(UUID idPurchase) {
        return purchaseBookRepository.findPurchaseBookByPurchase_IdPurchase(idPurchase);
    }

    @Override
    public PurchaseBook findPurchaseBookByBook(Book book) {
        return purchaseBookRepository.findPurchaseBookByBook(book);
    }

    @Override
    public Page<PurchaseBook> findByPurchase(UUID idPurchase, Pageable pageable) {
        return purchaseBookRepository.findByPurchase(idPurchase, pageable);
    }

    @Override
    public Page<PurchaseBook> findAllPurchaseBooks(Pageable pageable) {
        return purchaseBookRepository.findAll(pageable);
    }
}

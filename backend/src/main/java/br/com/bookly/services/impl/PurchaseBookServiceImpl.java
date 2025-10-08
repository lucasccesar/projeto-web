package br.com.bookly.services.impl;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Purchase;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.entities.dtos.PurchaseBookDTO;
import br.com.bookly.entities.dtos.PurchaseBookResponseDTO;
import br.com.bookly.repositories.BookRepository;
import br.com.bookly.repositories.PurchaseBookRepository;
import br.com.bookly.repositories.PurchaseRepository;
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

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public PurchaseBook createPurchaseBook(PurchaseBookDTO purchaseBookdto) {
        if (purchaseBookdto.getQuantity() == null) {
            return null;
        }

        if (purchaseBookdto.getIdBook() == null || purchaseBookdto.getPurchase() == null) {
            return null;
        }

        // Busca o Book e o Purchase pelos IDs
        Book book = bookRepository.findById(purchaseBookdto.getIdBook()).orElse(null);
        Purchase purchase = purchaseRepository.findById(purchaseBookdto.getPurchase()).orElse(null);

        if (book == null || purchase == null) {
            return null;
        }

        // Cria a entidade para salvar
        PurchaseBook purchaseBook = new PurchaseBook();
        purchaseBook.setBook(book);
        purchaseBook.setPurchase(purchase);
        purchaseBook.setUnitPrice(purchaseBookdto.getUnitPrice());
        purchaseBook.setQuantity(purchaseBookdto.getQuantity());

        // Agora sim, salva a ENTIDADE
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
    public PurchaseBook findPurchaseBookByBook_IdBook(UUID idbook) {
        return purchaseBookRepository.findPurchaseBookByBook_IdBook(idbook);
    }

    @Override
    public Page<PurchaseBookResponseDTO> findByPurchase_IdPurchase(UUID idPurchase, Pageable pageable) {
        Page<PurchaseBook> page = purchaseBookRepository.findByPurchase_IdPurchase(idPurchase, pageable);
        return page.map(PurchaseBookResponseDTO::new);
    }



    @Override
    public Page<PurchaseBookResponseDTO> findAllPurchaseBooks(Pageable pageable) {
        Page<PurchaseBook> page = purchaseBookRepository.findAll(pageable);
        return page.map(PurchaseBookResponseDTO::new);
    }
}

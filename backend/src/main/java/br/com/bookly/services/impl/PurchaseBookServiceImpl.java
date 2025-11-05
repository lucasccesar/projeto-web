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
import br.com.bookly.services.PurchaseService;
import br.com.bookly.services.bookService;
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
    public PurchaseBookRepository getPurchaseBookRepository() {
        return purchaseBookRepository;
    }

    @Override
    public PurchaseBook findPurchaseBookByPurchase_IdPurchase(UUID idPurchase) {
        return purchaseBookRepository.findPurchaseBookByPurchase_IdPurchase(idPurchase);
    }

    @Override
    public Page<PurchaseBookResponseDTO> findPurchaseBookByBook_IdBook(UUID idbook, Pageable pageable) {
        Page<PurchaseBook> pg =  purchaseBookRepository.findPurchaseBookByBook_IdBook(idbook, pageable);
        return pg.map(PurchaseBookResponseDTO::new) ;
    }

    @Override
    public Page<PurchaseBookResponseDTO> findByPurchase_IdPurchase(UUID idPurchase, Pageable pageable) {
        Page<PurchaseBook> page = purchaseBookRepository.findByPurchase_IdPurchase(idPurchase, pageable);
        return page.map(PurchaseBookResponseDTO::new);
    }
}
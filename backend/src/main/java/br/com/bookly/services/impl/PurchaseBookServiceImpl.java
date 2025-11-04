package br.com.bookly.services.impl;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Purchase;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.entities.dtos.PurchaseBookDTO;
import br.com.bookly.entities.dtos.PurchaseBookResponseDTO;
import br.com.bookly.exceptions.BadRequestException;
import br.com.bookly.exceptions.InexistentIdUserException;
import br.com.bookly.exceptions.InexistentPurchaseBookException;
import br.com.bookly.exceptions.InexistentPurchaseException;
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
    public PurchaseBook findPurchaseBook(UUID id) {
        if(id == null){
            throw new InexistentPurchaseBookException("Error: Purchase book ID must not be null");
        }
        PurchaseBook existingPurchaseBook = purchaseBookRepository.findById(id).orElse(null);
        if(existingPurchaseBook == null){
            throw new InexistentPurchaseBookException("Error: Purchase book ID does not exist");
        }
        return existingPurchaseBook;
    }

    @Override
    public PurchaseBook findPurchaseBookByPurchase_IdPurchase(UUID idPurchase) {
        if(idPurchase == null){
            throw new BadRequestException("Error: Purchase ID must not be null");
        }
        PurchaseBook exists = purchaseBookRepository.findPurchaseBookByPurchase_IdPurchase(idPurchase);

        if(exists == null){
            throw new InexistentPurchaseException("Error: Purchase with this id doesn't exist");
        }
        return exists;
    }

    @Override
    public Page<PurchaseBookResponseDTO> findPurchaseBookByBook_IdBook(UUID idbook, Pageable pageable) {
        if (idbook == null){
            throw new BadRequestException("Error: User ID must not be null");}

        Page<PurchaseBook> pg =  purchaseBookRepository.findPurchaseBookByBook_IdBook(idbook, pageable);

        if(pg.isEmpty()){
            throw new InexistentIdUserException("Error: User with this ID not found");
        }

        return pg.map(PurchaseBookResponseDTO::new) ;
    }

    @Override
    public Page<PurchaseBookResponseDTO> findByPurchase_IdPurchase(UUID idPurchase, Pageable pageable) {
        Page<PurchaseBook> page = purchaseBookRepository.findByPurchase_IdPurchase(idPurchase, pageable);
        return page.map(PurchaseBookResponseDTO::new);
    }
}
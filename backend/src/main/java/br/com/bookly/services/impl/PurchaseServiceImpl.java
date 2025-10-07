package br.com.bookly.services.impl;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Purchase;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.entities.Users;
import br.com.bookly.entities.dtos.PurchaseBookDTO;
import br.com.bookly.entities.dtos.PurchaseDTO;
import br.com.bookly.repositories.BookRepository;
import br.com.bookly.repositories.PurchaseBookRepository;
import br.com.bookly.repositories.PurchaseRepository;
import br.com.bookly.repositories.UsersRepository;
import br.com.bookly.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    PurchaseBookRepository purchaseBookRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public Purchase createPurchase(PurchaseDTO purchaseDTO) {
        Users user = userRepository.findById(purchaseDTO.getIdUser()).orElse(null);
        if (user == null) return null;

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setTotalValuation(purchaseDTO.getTotalValuation());

        Purchase savedPurchase = purchaseRepository.save(purchase);

        for (PurchaseBookDTO bookDTO : purchaseDTO.getBooks()) {
            Book book = bookRepository.findById(bookDTO.getIdBook()).orElse(null);
            if (book == null) continue;

            PurchaseBook purchaseBook = new PurchaseBook();
            purchaseBook.setPurchase(savedPurchase);
            purchaseBook.setBook(book);
            purchaseBook.setQuantity(bookDTO.getQuantity());
            purchaseBook.setUnitPrice(bookDTO.getUnitPrice());

            purchaseBookRepository.save(purchaseBook);
            savedPurchase.getPurchaseBooks().add(purchaseBook);
        }

        return purchaseRepository.save(savedPurchase);
    }


    @Override
    public boolean deletePurchase(UUID id) {
        if(!purchaseRepository.existsById(id)){
            return false;
        }

        purchaseRepository.deleteById(id);
        return true;
    }

    @Override
    public Purchase updatePurchase(UUID id, Purchase purchase) {
        Purchase exists = purchaseRepository.findById(id).orElse(null);

        if(exists==null){
            return null;
        }

        if(purchase.getUser() == null){
            return null;
        }

        exists.setUser((purchase.getUser()));

        if(purchase.getPurchaseBooks() == null){
            return null;
        }
        exists.getPurchaseBooks().clear();
        exists.getPurchaseBooks().addAll(purchase.getPurchaseBooks());
        purchase.getPurchaseBooks().forEach(pb -> pb.setPurchase(exists));
        /*Dificuldade nessa parte
         O forEach tem uma função lambda embutida
         Para cada PurchaseBook da lista, define que ele pertence a esta Purchase.
         Garante que a relação bidirecional entre Purchase e PurchaseBook fique correta.
        */
        return purchaseRepository.save(exists);

    }

    @Override
    public Purchase findPurchaseById(UUID id) {
        return purchaseRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Purchase> findPurchaseByUser_id(UUID id, Pageable pageable) {
        if(id == null){
            return purchaseRepository.findAll(pageable);
        }

        return purchaseRepository.findByUserId_id(id, pageable);
        // Dúvida, se será preciso colocar o pageable.
        /* O pageable será para os usuários ou para as purchases, pq se
            for para os usuários não será necessário, mas se for para as purchases
            fará sentido
         */
    }


    @Override
    public Purchase findPurchaseByDate_purchaseDate(LocalDate date) {
        return  purchaseRepository.findBypurchaseDate(date);
    }

    @Override
    public Page<Purchase> findAllPurchase(Pageable pageable) {
        return purchaseRepository.findAll(pageable);
    }

}
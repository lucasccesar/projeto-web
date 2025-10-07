package br.com.bookly.services;

import br.com.bookly.entities.Purchase;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.entities.dtos.PurchaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface PurchaseService {

    public Purchase createPurchase(PurchaseDTO purchaseDTO);
    public boolean deletePurchase(UUID id);
    public Purchase updatePurchase(UUID id,Purchase purchase);
    public Purchase findPurchaseById(UUID id);
    public Page<Purchase> findPurchaseByUser_id(UUID id, Pageable pageable);
    public Page<Purchase> findAllPurchase(Pageable pageable);
    public Purchase findPurchaseByDate_purchaseDate(LocalDate date);
}
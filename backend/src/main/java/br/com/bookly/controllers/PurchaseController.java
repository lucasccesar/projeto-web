package br.com.bookly.controllers;

import br.com.bookly.entities.Purchase;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.entities.dtos.PurchaseDTO;
import br.com.bookly.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseDTO purchasedto) {
        Purchase createdPurchase = purchaseService.createPurchase(purchasedto);

        if (createdPurchase == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(createdPurchase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Purchase> deletePurchase(@PathVariable UUID id) {
        boolean deleted = purchaseService.deletePurchase(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable UUID id, @RequestBody Purchase purchase) {

        Purchase updatedPurchase = purchaseService.updatePurchase(id, purchase);

        if (updatedPurchase == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPurchase);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Purchase> findById(@PathVariable UUID id) {
        Purchase purchase = purchaseService.findPurchaseById(id);
        if (purchase == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(purchase);

    }

    @GetMapping("/{date}")
    public ResponseEntity<Purchase> findPurchaseByDate_purchaseDate(@RequestParam LocalDate date) {
        Purchase purchase = purchaseService.findPurchaseByDate_purchaseDate(date);
        if (purchase == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/{pageId}")
    public ResponseEntity<Page<Purchase>> findPurchaseByUser_id(@PathVariable UUID id, Pageable pageable) {
        Page<Purchase> purchase = purchaseService.findPurchaseByUser_id(id, pageable);
        if (purchase == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(purchase);
    }

    @GetMapping
    public ResponseEntity<Page<Purchase>> findAll(Pageable pageable) {
        Page<Purchase> page = purchaseService.findAllPurchase(pageable);
        return ResponseEntity.ok(page);
    }
}

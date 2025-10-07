package br.com.bookly.controllers;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.services.PurchaseBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/purchaseBook")
public class PurchaseBookController {

    @Autowired
    PurchaseBookService purchaseBookService;

    @PostMapping
    public ResponseEntity<PurchaseBook> createPurchaseBook(@RequestBody PurchaseBook purchaseBook){
        PurchaseBook createdPurchaseBook = purchaseBookService.createPurchaseBook(purchaseBook);

        if(createdPurchaseBook == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(createdPurchaseBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PurchaseBook> deletePurchaseBookById(@RequestBody UUID idPurchaseBook){
        boolean delete = purchaseBookService.deletePurchaseBook(idPurchaseBook);

        if(delete){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<PurchaseBook> updatePurchaseBook(@PathVariable UUID id, @RequestBody PurchaseBook purchaseBook){
        PurchaseBook updatedPurchaseBook = purchaseBookService.updatePurchaseBook(id, purchaseBook);

        if(updatedPurchaseBook == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(updatedPurchaseBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseBook> findById(@PathVariable UUID id) {
        PurchaseBook purchaseBook = purchaseBookService.findPurchaseBookById(id);
        if (purchaseBook == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(purchaseBook);
    }


    @GetMapping("/purchase")
    public ResponseEntity<PurchaseBook> findByPurchase_IdPurchase(@PathVariable UUID idPurchase) {
        PurchaseBook purchaseBook = purchaseBookService.findPurchaseBookByPurchase_IdPurchase(idPurchase);
        if (purchaseBook == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(purchaseBook);
    }


    @GetMapping("/book")
    public ResponseEntity<PurchaseBook> findByBook(@PathVariable Book book) {
            PurchaseBook purchaseBook = purchaseBookService.findPurchaseBookByBook(book);
        if (purchaseBook == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(purchaseBook);
    }

    @GetMapping("/bypurchase")
    public ResponseEntity<Page<PurchaseBook>> findAllByPurchase(@PathVariable UUID idPurchase, Pageable pageable) {
         Page<PurchaseBook> page = purchaseBookService.findByPurchase(idPurchase, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseBook>> findAll(Pageable pageable) {
        Page<PurchaseBook> page = purchaseBookService.findAllPurchaseBooks(pageable);
        return ResponseEntity.ok(page);
    }
}

package br.com.bookly.controllers;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.entities.dtos.PurchaseBookDTO;
import br.com.bookly.entities.dtos.PurchaseBookResponseDTO;
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
    public ResponseEntity<PurchaseBookResponseDTO> createPurchaseBook(@RequestBody PurchaseBookDTO dto) {
        PurchaseBook created = purchaseBookService.createPurchaseBook(dto);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        // Converte a entidade para DTO de resposta
        PurchaseBookResponseDTO response = new PurchaseBookResponseDTO(created);

        return ResponseEntity.status(201).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PurchaseBook> deletePurchaseBookById(@PathVariable UUID id){
        boolean delete = purchaseBookService.deletePurchaseBook(id);

        if(delete){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseBookResponseDTO> updatePurchaseBook(@PathVariable UUID id, @RequestBody PurchaseBook purchaseBook){
        PurchaseBook updatedPurchaseBook = purchaseBookService.updatePurchaseBook(id, purchaseBook);

        if(updatedPurchaseBook == null){
            return ResponseEntity.badRequest().build();
        }

        PurchaseBookResponseDTO response = new PurchaseBookResponseDTO(updatedPurchaseBook);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseBookResponseDTO> findById(@PathVariable UUID id) {
        PurchaseBook purchaseBook = purchaseBookService.findPurchaseBookById(id);

        if (purchaseBook == null)
            return ResponseEntity.notFound().build();

        PurchaseBookResponseDTO dto = new PurchaseBookResponseDTO(purchaseBook);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/purchase/{idPurchase}")
    public ResponseEntity<PurchaseBookResponseDTO> findByPurchase_IdPurchase(@PathVariable UUID idPurchase) {
        PurchaseBook purchaseBook = purchaseBookService.findPurchaseBookByPurchase_IdPurchase(idPurchase);
        if (purchaseBook == null)
            return ResponseEntity.notFound().build();

        PurchaseBookResponseDTO dto = new PurchaseBookResponseDTO(purchaseBook);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/book/{idBook}")
    public ResponseEntity<PurchaseBookResponseDTO> findPurchaseBookBy_Id(@PathVariable UUID idBook) {
        PurchaseBook purchaseBook = purchaseBookService.findPurchaseBookByBook_IdBook(idBook);

        if (purchaseBook == null)
            return ResponseEntity.notFound().build();

        PurchaseBookResponseDTO dto = new PurchaseBookResponseDTO(purchaseBook);
        return ResponseEntity.ok(dto);

        //Da erro quando um livro está em mais de uma lista de compra de livros, rever isso!
    }

    @GetMapping("/bypurchase/{idPurchase}")
    public ResponseEntity<Page<PurchaseBookResponseDTO>> findAllByPurchase(@PathVariable UUID idPurchase, Pageable pageable) {
         Page<PurchaseBookResponseDTO> page = purchaseBookService.findByPurchase_IdPurchase(idPurchase, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseBookResponseDTO>> findAll(Pageable pageable) {
        Page<PurchaseBookResponseDTO> page = purchaseBookService.findAllPurchaseBooks(pageable);
        return ResponseEntity.ok(page);
    }
}

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



    @GetMapping("/purchase/{idPurchase}")
    public ResponseEntity<PurchaseBookResponseDTO> findByPurchase_IdPurchase(@PathVariable UUID idPurchase) {
        PurchaseBook purchaseBook = purchaseBookService.findPurchaseBookByPurchase_IdPurchase(idPurchase);
        if (purchaseBook == null)
            return ResponseEntity.notFound().build();

        PurchaseBookResponseDTO dto = new PurchaseBookResponseDTO(purchaseBook);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/book/{idBook}")
    public ResponseEntity<Page<PurchaseBookResponseDTO>> findPurchaseBookBy_Id(@PathVariable UUID idBook, Pageable pageable) {
        Page<PurchaseBookResponseDTO> purchaseBook = purchaseBookService.findPurchaseBookByBook_IdBook(idBook, pageable);

        if (purchaseBook == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(purchaseBook);

        //Da erro quando um livro está em mais de uma lista de compra de livros, rever isso!
    }

    @GetMapping("/bypurchase/{idPurchase}")
    public ResponseEntity<Page<PurchaseBookResponseDTO>> findAllByPurchase(@PathVariable UUID idPurchase, Pageable pageable) {
         Page<PurchaseBookResponseDTO> page = purchaseBookService.findByPurchase_IdPurchase(idPurchase, pageable);
        return ResponseEntity.ok(page);
    }
}

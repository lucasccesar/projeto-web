package br.com.bookly.services;


import br.com.bookly.entities.PurchaseBook;
import br.com.bookly.entities.dtos.PurchaseBookDTO;
import br.com.bookly.entities.dtos.PurchaseBookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface PurchaseBookService {
    public PurchaseBook createPurchaseBook(PurchaseBookDTO purchaseBookDto);
    public boolean deletePurchaseBook(UUID id);
    public PurchaseBook updatePurchaseBook(UUID id, PurchaseBook purchaseBook);
    public PurchaseBook findPurchaseBookById(UUID id);
    public PurchaseBook findPurchaseBookByPurchase_IdPurchase(UUID idPurchase);
    public PurchaseBook findPurchaseBookByBook_IdBook(UUID idbook);
    public Page<PurchaseBookResponseDTO> findByPurchase_IdPurchase(UUID idPurchase, Pageable pageable);
    public Page<PurchaseBookResponseDTO> findAllPurchaseBooks(Pageable pageable);


}

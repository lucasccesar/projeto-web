package br.com.bookly.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PurchaseBookDTO {
    private UUID idBook;
    private Integer quantity;

    public PurchaseBookDTO() {
    }

    public PurchaseBookDTO(UUID idBook, Integer quantity) {
        this.idBook = idBook;
        this.quantity = quantity;
    }
}

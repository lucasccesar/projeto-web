package br.com.bookly.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class PurchaseBookDTO {
    private UUID idBook;
    private Integer quantity;
    private BigDecimal unitPrice;

    public PurchaseBookDTO() {
    }

    public PurchaseBookDTO(UUID idBook, Integer quantity,  BigDecimal unitPrice) {
        this.idBook = idBook;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}

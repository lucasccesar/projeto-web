package br.com.bookly.entities.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseDTO {

    private UUID idUser;
    private BigDecimal totalValuation;
    private List<PurchaseBookDTO> books;

    public PurchaseDTO(UUID idUser, BigDecimal totalValuation, List<PurchaseBookDTO> books) {
        this.idUser = idUser;
        this.totalValuation = totalValuation;
        this.books = books;
    }
}

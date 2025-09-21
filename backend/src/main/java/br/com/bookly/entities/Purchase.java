package br.com.bookly.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPurchase;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users idUser;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false, columnDefinition = "NUMERIC(4,2)")
    private BigDecimal totalValuation;

}

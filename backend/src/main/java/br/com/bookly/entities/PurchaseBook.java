package br.com.bookly.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "Compra_Livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class PurchaseBook {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPurchaseBook;

    @OneToMany
    @JoinColumn(name = "compra", nullable = false)
    private Purchase purchase;

    @ManyToMany
    @JoinColumn(name = "livro", nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "NUMERIC(4,2)")
    private BigDecimal unitPrice;
}

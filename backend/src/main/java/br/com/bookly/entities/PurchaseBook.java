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
    private UUID id_compraLivro;

    @OneToMany
    @JoinColumn(name = "compra", nullable = false)
    private Purchase compra;

    @ManyToMany
    @JoinColumn(name = "livro", nullable = false)
    private Book livro;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, columnDefinition = "NUMERIC(4,2)")
    private BigDecimal precoUnidade;
}

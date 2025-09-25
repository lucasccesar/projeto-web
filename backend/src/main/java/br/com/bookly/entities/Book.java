package br.com.bookly.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Livro")
public class Book{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_livro")
    private UUID idBook;

    @Column(name = "sinopse",nullable = false)
    private String synopsis;

    @Column(name = "titulo",nullable = false)
    private String title;

    @Column(name = "genero",nullable = false)
    private String genre;

    @Column(nullable = false, name="quantidade_disponivel")
    private int availableQuantity;

    @Column(name = "preco",nullable = false, columnDefinition = "NUMERIC(6,2)")
    private BigDecimal price;

    // Relação de livro com avaliação (Evaluation)
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();

    // Relação de livro com LivroCompra (PurchaseBook)
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseBook> purchaseBooks = new ArrayList<>();
}
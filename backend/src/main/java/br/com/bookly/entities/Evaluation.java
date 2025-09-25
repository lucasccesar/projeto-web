package br.com.bookly.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avaliacao")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_avaliacao")
    private UUID idEvaluation;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Book book;

    @Column(name = "comentario",nullable = false)
    private String coments;

    @Column(name = "nota",nullable = false)
    private Integer Grade;

    @Column(name = "data_avaliacao",nullable = false)
    private LocalDate evaluationDate;
}

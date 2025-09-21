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

public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID evaluation;

    @OneToMany
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users idUser;

    @ManyToMany
    @JoinColumn(name = "id_livro", nullable = false)
    private Book idBook;

    @Column(nullable = false)
    private String coments;

    @Column(nullable = false)
    private Integer Grade;

    @Column(nullable = false)
    private LocalDate evaluationDate;
}

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
    private UUID avaliacao;

    @OneToMany
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users idUsuario;

    @ManyToMany
    @JoinColumn(name = "id_livro", nullable = false)
    private Book idLivro;

    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private Integer nota;

    @Column(nullable = false)
    private LocalDate dataAvaliacao;
}

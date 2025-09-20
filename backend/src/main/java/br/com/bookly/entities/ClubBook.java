package br.com.bookly.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "livro_clube")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubBook { // Livro_clube

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_livro_clube")
    private UUID idClubBook;

    @ManyToOne
    @JoinColumn(name = "id_clube_do_livro", nullable = false)
    private BookClub clubeDoLivro;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Book livro;

    @Column(nullable = false, name = "data_inicio")
    private LocalDate dataInicio;

    @Column(nullable = false, name = "data_fim")
    private LocalDate dataFim;

}

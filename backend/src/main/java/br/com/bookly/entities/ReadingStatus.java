package br.com.bookly.entities;

import br.com.bookly.entities.Enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReadingStatus {

    private UUID statusLeitura;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users idUsuario;

    @OneToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Book idLivro;

    @Column(nullable = false)
    private Status status;
}

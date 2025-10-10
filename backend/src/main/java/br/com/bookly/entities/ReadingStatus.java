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
@Table(name = "Status_Leitura")
public class ReadingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_status_leitura")
    private UUID idReadingStatus;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users users;

    @OneToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}

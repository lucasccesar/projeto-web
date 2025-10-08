package br.com.bookly.entities;

import br.com.bookly.entities.Enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuarios")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario")
    private UUID id;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(name = "data_nascimento",nullable = false)
    private LocalDate birthday;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "senha",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo",nullable = false)
    private UserType type;

    // relação de usuário com ClubMessage
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMessage> MensagensDoUsuario = new ArrayList<>();

    // relação de usuário com ParticipantUser
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipantUser> usuariosParticipantes = new ArrayList<>();

    // relação de usuário com avaliação (Evaluation)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "livros_favoritos",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_livro")
    )
    private Set<Book> favoriteBooks = new HashSet<>();
}
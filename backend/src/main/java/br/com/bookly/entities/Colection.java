package br.com.bookly.entities;

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
@Table(name = "Colecao")
public class Colection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_colecao")
    private UUID idColection;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users user;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(name = "descricao",nullable = false)
    private String description;


}

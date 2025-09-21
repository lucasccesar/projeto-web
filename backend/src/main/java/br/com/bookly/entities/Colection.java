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

public class Colection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idColection;

    @ManyToOne
    private Users idUser;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;


}

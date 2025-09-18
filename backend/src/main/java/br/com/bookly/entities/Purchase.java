package br.com.bookly.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCompra;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Users idUsuario;

    @Column(nullable = false)
    private LocalDate dataCompra;

    @Column(nullable = false, columnDefinition = "NUMERIC(4,2)")
    private BigDecimal valorTotal;

}

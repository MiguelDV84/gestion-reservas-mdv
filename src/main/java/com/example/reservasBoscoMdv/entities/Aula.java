package com.example.reservasBoscoMdv.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    @NotNull(message = "La capacidad no puede estar en blanco")
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "es_aula_ordenador", nullable = false)
    private boolean esAulaOrdenador;

    @NotNull(message = "El número de ordenadores no puede estar en blanco")
    @Column(name = "num_ordenadores")
    @Min(value = 0, message = "El número de ordenadores no puede ser negativo")
    private Integer numOrdenadores;

    @OneToMany(mappedBy = "aula")
    @JsonManagedReference(value = "aula-reservas")
    private List<Reserva> reservas;

}

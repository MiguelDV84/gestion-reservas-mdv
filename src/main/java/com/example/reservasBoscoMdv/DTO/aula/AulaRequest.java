package com.example.reservasBoscoMdv.DTO.aula;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AulaRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "La capacidad no puede ser nula")
        @Min(value = 3, message = "La capacidad mínima es 3")
        Integer capacidad,

        @NotNull(message = "El campo esAulaOrdenador no puede ser nulo")
        Boolean esAulaOrdenador,

        @NotNull(message = "El número de ordenadores no puede ser nulo")
        @PositiveOrZero(message = "El número de ordenadores no puede ser negativo")
        Integer numOrdenadores
) {}

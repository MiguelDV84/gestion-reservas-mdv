package com.example.reservasBoscoMdv.DTO.reserva;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ReservaRequest(
        String motivo,

        @NotNull(message = "El número de asistentes no puede ser nulo")
        @Min(value = 3, message = "El número de asistentes debe ser al menos 3")
        Integer numAsistentes,

        @NotNull(message = "La fecha de reserva no puede ser nula")
        @FutureOrPresent(message = "La fecha no puede ser anterior a hoy")
        LocalDate fechaReserva,

        @NotNull(message = "El aula no puede ser nula")
        Long aulaId,

        @NotNull(message = "El tramo horario no puede ser nulo")
        Long tramoId,

        @NotNull(message = "El usuario no puede ser nulo")
        Long usuarioId
) {
}

package com.example.reservasBoscoMdv.DTO.tramoHorario;

import com.example.reservasBoscoMdv.enums.DiaSemana;
import com.example.reservasBoscoMdv.enums.TipoTramo;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TramoHorarioRequest(
        @NotNull(message = "El día de la semana no puede ser nulo")
        DiaSemana diaSemana,

        @NotNull(message = "La hora de inicio no puede ser nula")
        LocalTime horaInicio,

        @NotNull(message = "La hora de fin no puede ser nula")
        LocalTime horaFin,

        @NotNull(message = "El tipo de tramo no puede ser nulo")
        TipoTramo tipoTramo,

        Long aulaId
) {
}

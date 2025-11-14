package com.example.reservasBoscoMdv.DTO.tramoHorario;

import com.example.reservasBoscoMdv.enums.DiaSemana;
import com.example.reservasBoscoMdv.enums.TipoTramo;

import java.time.LocalTime;

public record TramoHorarioRequest(
        DiaSemana diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin,
        TipoTramo tipoTramo,
        Long aulaId
) {
}

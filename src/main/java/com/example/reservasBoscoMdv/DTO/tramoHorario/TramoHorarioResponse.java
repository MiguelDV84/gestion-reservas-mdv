package com.example.reservasBoscoMdv.DTO.tramoHorario;

import com.example.reservasBoscoMdv.entities.TramoHorario;
import com.example.reservasBoscoMdv.enums.DiaSemana;
import com.example.reservasBoscoMdv.enums.TipoTramo;

import java.time.LocalTime;

public record TramoHorarioResponse(
        Long id,
        DiaSemana diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin,
        TipoTramo tipoTramo
) {
    public static TramoHorarioResponse fromEntity(TramoHorario tramoHorario) {
        return new TramoHorarioResponse(
                tramoHorario.getId(),
                tramoHorario.getDiaSemana(),
                tramoHorario.getHoraInicio(),
                tramoHorario.getHoraFin(),
                tramoHorario.getTipoTramo()
        );
    }
}

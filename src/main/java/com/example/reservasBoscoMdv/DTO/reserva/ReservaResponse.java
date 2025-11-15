package com.example.reservasBoscoMdv.DTO.reserva;

import com.example.reservasBoscoMdv.DTO.aula.AulaResponse;
import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioResponse;
import com.example.reservasBoscoMdv.DTO.usuario.UsuarioResponse;
import com.example.reservasBoscoMdv.entities.Reserva;

import java.time.LocalDate;

public record ReservaResponse(
        Long id,
        String motivo,
        Integer numAsistentes,
        LocalDate fechaCreacion,
        LocalDate fechaReserva,
        AulaResponse aula,
        TramoHorarioResponse tramo,
        UsuarioResponse usuario
) {
    public static ReservaResponse fromEntity(Reserva Reserva) {
        return new ReservaResponse(
                Reserva.getId(),
                Reserva.getMotivo(),
                Reserva.getNumAsistentes(),
                Reserva.getFechaCreacion(),
                Reserva.getFechaReserva(),
                AulaResponse.fromEntity(Reserva.getAula()),
                TramoHorarioResponse.fromEntity(Reserva.getTramoHorario()),
                UsuarioResponse.fromEntity(Reserva.getUsuario())
        );
    }
}

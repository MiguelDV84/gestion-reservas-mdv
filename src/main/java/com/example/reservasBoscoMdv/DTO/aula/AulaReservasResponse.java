package com.example.reservasBoscoMdv.DTO.aula;

import com.example.reservasBoscoMdv.DTO.reserva.ReservaSimpleResponse;
import com.example.reservasBoscoMdv.entities.Aula;

import java.util.List;

public record AulaReservasResponse(
        Long id,
        String nombre,
        Integer capacidad,
        Boolean esAulaOrdenador,
        Integer numOrdenadores,
        List<ReservaSimpleResponse> reservas
) {
    public static AulaReservasResponse fromEntity(Aula aula) {
        return new AulaReservasResponse(
                aula.getId(),
                aula.getNombre(),
                aula.getCapacidad(),
                aula.isEsAulaOrdenador(),
                aula.getNumOrdenadores(),
                aula.getReservas()
                        .stream()
                        .map(ReservaSimpleResponse::fromEntity)
                        .toList()
        );
    }
}

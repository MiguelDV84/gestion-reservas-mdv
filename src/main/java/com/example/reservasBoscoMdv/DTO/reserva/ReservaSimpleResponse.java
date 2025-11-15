package com.example.reservasBoscoMdv.DTO.reserva;

import com.example.reservasBoscoMdv.entities.Reserva;

import java.time.LocalDate;

public record ReservaSimpleResponse(
        Long id,
        String motivo,
        Integer numAsistentes,
        LocalDate fechaCreacion,
        LocalDate fechaReserva
) {
    public static ReservaSimpleResponse fromEntity(Reserva r) {
        return new ReservaSimpleResponse(
                r.getId(),
                r.getMotivo(),
                r.getNumAsistentes(),
                r.getFechaCreacion(),
                r.getFechaReserva()
        );
    }
}

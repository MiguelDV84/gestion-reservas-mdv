package com.example.reservasBoscoMdv.DTO.reserva;

public record ReservaRequest(
        String motivo,
        Integer numAsistentes,
        Long aulaId,
        Long tramoId,
        Long usuarioId
) {
}

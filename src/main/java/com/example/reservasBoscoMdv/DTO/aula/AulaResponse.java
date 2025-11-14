package com.example.reservasBoscoMdv.DTO.aula;

public record AulaResponse(
        Long id,
        String nombre,
        Integer capacidad,
        boolean esAulaOrdenador,
        Integer numOrdenadores
) {
}

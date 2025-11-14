package com.example.reservasBoscoMdv.DTO.aula;

public record AulaRequest(
        String nombre,
        Integer capacidad,
        boolean esAulaOrdenador,
        Integer numOrdenadores
) {
}

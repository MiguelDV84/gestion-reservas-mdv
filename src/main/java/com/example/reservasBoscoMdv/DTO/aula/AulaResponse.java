package com.example.reservasBoscoMdv.DTO.aula;

import com.example.reservasBoscoMdv.entities.Aula;

public record AulaResponse(
        Long id,
        String nombre,
        Integer capacidad,
        boolean esAulaOrdenador,
        Integer numOrdenadores
) {
    public static AulaResponse fromEntity(Aula aula) {
        return new AulaResponse(
                aula.getId(),
                aula.getNombre(),
                aula.getCapacidad(),
                aula.isEsAulaOrdenador(),
                aula.getNumOrdenadores()
        );
    }
}

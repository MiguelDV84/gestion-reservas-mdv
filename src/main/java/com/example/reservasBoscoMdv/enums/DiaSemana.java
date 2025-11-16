package com.example.reservasBoscoMdv.enums;

import java.time.DayOfWeek;

public enum DiaSemana {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES;

    public static DiaSemana convertir(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> DiaSemana.LUNES;
            case TUESDAY -> DiaSemana.MARTES;
            case WEDNESDAY -> DiaSemana.MIERCOLES;
            case THURSDAY -> DiaSemana.JUEVES;
            case FRIDAY -> DiaSemana.VIERNES;
            default -> throw new IllegalArgumentException("No se permiten sábados ni domingos");
        };
    }
}

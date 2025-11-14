package com.example.reservasBoscoMdv.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    UNIQUE_TRAMO("tramo_horario.unique_tramo"),
    TRAMO_DUPLICADO("TRAMO_DUPLICADO");

    private final String code;
}

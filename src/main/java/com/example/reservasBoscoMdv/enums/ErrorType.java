package com.example.reservasBoscoMdv.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION", "Se ha producido un error de integridad de datos."),
    UNIQUE_TRAMO("tramo_horario.unique_tramo",""),
    TRAMO_DUPLICADO("TRAMO_DUPLICADO", "El tramo horario ya existe para el día especificado."),
    VALIDATION_ERROR("VALIDATION_ERROR","Error de validación en los datos proporcionados."),
    AULA_CAPACIDAD_EXCEDIDA("AULA_CAPACIDAD_EXCEDIDA", "El número de asistentes excede la capacidad del aula."),
    RESERVA_DUPLICADA("RESERVA_DUPLICADA", "Ya existe una reserva para el aula en la fecha y tramo horario especificados."),
    JSON_PARSE_ERROR("JSON_PARSE_ERROR", "Error al analizar el JSON de entrada."),
    AULA_NO_ENCONTRADA("AULA_NO_ENCONTRADA", "Aula no encontrada."),;

    private final String code;
    private final String message;
}

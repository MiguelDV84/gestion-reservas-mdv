package com.example.reservasBoscoMdv.DTO.reserva;

import com.example.reservasBoscoMdv.DTO.aula.AulaResponse;
import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioResponse;
import com.example.reservasBoscoMdv.DTO.usuario.UsuarioResponse;

import java.time.LocalDate;

public record ReservaResponse(
        Long id,
        String motivo,
        Integer numAsistentes,
        LocalDate fechaCreacion,
        AulaResponse aula, //Crear Aula Response
        TramoHorarioResponse tramo, // Crear TramoHorario Response
        UsuarioResponse usuario
) {}

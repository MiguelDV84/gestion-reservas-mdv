package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.reserva.ReservaRequest;
import com.example.reservasBoscoMdv.DTO.reserva.ReservaResponse;
import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.entities.Reserva;
import com.example.reservasBoscoMdv.entities.TramoHorario;
import com.example.reservasBoscoMdv.entities.Usuario;
import com.example.reservasBoscoMdv.enums.DiaSemana;
import com.example.reservasBoscoMdv.enums.ErrorType;
import com.example.reservasBoscoMdv.errors.BusinessException;
import com.example.reservasBoscoMdv.repositories.IReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final IReservaRepository reservaRepository;
    private final AulaService aulaService;
    private final TramoHorarioService tramoHorarioService;
    private final UsuarioService usuarioService;

    public ReservaResponse findById(Long id) {
        return reservaRepository.findById(id)
                .map(ReservaResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(
                        ErrorType.RESERVA_NO_ENCONTRADA.getCode(),
                        ErrorType.RESERVA_NO_ENCONTRADA.getMessage()
                ));
    }

    public List<ReservaResponse> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaResponse::fromEntity)
                .toList();
    }

    public List<ReservaResponse> findAllByUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(ReservaResponse::fromEntity)
                .toList();
    }

    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }

    public ReservaResponse update(Long id, ReservaRequest request) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorType.RESERVA_NO_ENCONTRADA.getCode(),
                        ErrorType.RESERVA_NO_ENCONTRADA.getMessage())
                );
        Aula aula = aulaService.findEntityById(request.aulaId());
        TramoHorario tramo = tramoHorarioService.findEntityById(request.tramoId());
        DiaSemana diaSemana = DiaSemana.convertir(request.fechaReserva().getDayOfWeek());

        validarReserva(request, aula, tramo, diaSemana);

        Reserva reservaUpdated = reservaRepository.save(Reserva.builder()
                .id(reserva.getId())
                .motivo(request.motivo())
                .numAsistentes(request.numAsistentes())
                .fechaCreacion(reserva.getFechaCreacion())
                .fechaReserva(request.fechaReserva())
                .aula(aula)
                .tramoHorario(tramo)
                .usuario(reserva.getUsuario())
                .build());

        return ReservaResponse.fromEntity(reservaUpdated);
    }

    public ReservaResponse insert(ReservaRequest request) {
        Aula aula = aulaService.findEntityById(request.aulaId());
        TramoHorario tramo = tramoHorarioService.findEntityById(request.tramoId());
        Usuario usuario = usuarioService.findEntityById(request.usuarioId());
        DiaSemana diaSemana = DiaSemana.convertir(request.fechaReserva().getDayOfWeek());

        validarReserva(request, aula, tramo, diaSemana);

        Reserva saved = reservaRepository.save(Reserva.builder()
                .motivo(request.motivo())
                .numAsistentes(request.numAsistentes())
                .fechaReserva(request.fechaReserva())
                .aula(aula)
                .tramoHorario(tramo)
                .usuario(usuario)
                .build());

        return ReservaResponse.fromEntity(saved);
    }

    private void validarReserva(ReservaRequest request, Aula aula, TramoHorario tramo, DiaSemana diaSemana) {
        if (request.numAsistentes() > aula.getCapacidad()) {
            throw new BusinessException(
                    ErrorType.AULA_CAPACIDAD_EXCEDIDA.getCode(),
                    ErrorType.AULA_CAPACIDAD_EXCEDIDA.getMessage()
            );
        }

        if (reservaRepository.existsByAulaAndFechaReservaAndTramoHorario(aula.getId(), request.fechaReserva(), tramo.getId())) {
            throw new BusinessException(
                    ErrorType.RESERVA_DUPLICADA.getCode(),
                    ErrorType.RESERVA_DUPLICADA.getMessage()
            );
        }

        if(tramo.getDiaSemana() != diaSemana ) {
            throw new BusinessException(
                    ErrorType.RESERVA_TRAMO_DIA_INCORRECTO.getCode(),
                    ErrorType.RESERVA_TRAMO_DIA_INCORRECTO.getMessage()
            );
        }

    }
}

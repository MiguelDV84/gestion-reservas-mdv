package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.reserva.ReservaRequest;
import com.example.reservasBoscoMdv.DTO.reserva.ReservaResponse;
import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.entities.Reserva;
import com.example.reservasBoscoMdv.entities.TramoHorario;
import com.example.reservasBoscoMdv.entities.Usuario;
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
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public List<ReservaResponse> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaResponse::fromEntity)
                .toList();
    }

    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }

    public ReservaResponse update(Long id, ReservaRequest request) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        Aula aula = aulaService.findEntityById(request.aulaId());
        TramoHorario tramo = tramoHorarioService.findEntityById(request.tramoId());

        validarReserva(request, aula, tramo);

        Reserva reservaUpdated = reservaRepository.save(Reserva.builder()
                .id(reserva.getId())
                .motivo(reserva.getMotivo())
                .numAsistentes(reserva.getNumAsistentes())
                .fechaReserva(reserva.getFechaReserva())
                .aula(reserva.getAula())
                .tramoHorario(reserva.getTramoHorario())
                .usuario(reserva.getUsuario())
                .build());

        return ReservaResponse.fromEntity(reservaUpdated);
    }

    public ReservaResponse insert(ReservaRequest request) {
        Aula aula = aulaService.findEntityById(request.aulaId());
        TramoHorario tramo = tramoHorarioService.findEntityById(request.tramoId());
        Usuario usuario = usuarioService.findEntityById(request.usuarioId());

        validarReserva(request, aula, tramo);

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

    private void validarReserva(ReservaRequest request, Aula aula, TramoHorario tramo) {
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
    }
}

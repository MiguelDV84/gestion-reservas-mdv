package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioRequest;
import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioResponse;
import com.example.reservasBoscoMdv.entities.TramoHorario;
import com.example.reservasBoscoMdv.repositories.ITramoHorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TramoHorarioService {

    private final ITramoHorarioRepository tramoHorarioRepository;

    public TramoHorario findEntityById(Long id) {
        return tramoHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tramo horario no encontrado"));
    }

    public Optional<TramoHorarioResponse> findById(Long id) {
        return tramoHorarioRepository.findById(id)
                .map(TramoHorarioResponse::fromEntity);
    }

    public List<TramoHorarioResponse> findAll() {
        return tramoHorarioRepository.findAll()
                .stream()
                .map(TramoHorarioResponse::fromEntity)
                .toList();
    }

    public Optional<TramoHorarioResponse> insert(TramoHorarioRequest tramoHorarioRequest) {
        TramoHorario tramoHorario = TramoHorario.builder()
                .diaSemana(tramoHorarioRequest.diaSemana())
                .horaInicio(tramoHorarioRequest.horaInicio())
                .horaFin(tramoHorarioRequest.horaFin())
                .tipoTramo(tramoHorarioRequest.tipoTramo())
                .aula(null)
                .build();
        TramoHorario savedTramoHorario = tramoHorarioRepository.save(tramoHorario);

        TramoHorarioResponse response = TramoHorarioResponse.fromEntity(savedTramoHorario);

        return Optional.of(response);
    }

    public void deleteById(Long id) {
        tramoHorarioRepository.deleteById(id);
    }

    public Optional<TramoHorarioResponse> update(Long id, TramoHorarioRequest tramoHorarioRequest) {
        return tramoHorarioRepository.findById(id).map(existingTramoHorario -> {
            existingTramoHorario.setDiaSemana(tramoHorarioRequest.diaSemana());
            existingTramoHorario.setHoraInicio(tramoHorarioRequest.horaInicio());
            existingTramoHorario.setHoraFin(tramoHorarioRequest.horaFin());
            existingTramoHorario.setTipoTramo(tramoHorarioRequest.tipoTramo());

            return TramoHorarioResponse.fromEntity(existingTramoHorario);
        });
    }
}

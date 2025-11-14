package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioRequest;
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

    public Optional<TramoHorario> findById(Long id) {
        return tramoHorarioRepository.findById(id);
    }

    public List<TramoHorario> findAll() {
        return tramoHorarioRepository.findAll();
    }

    public Optional<TramoHorario> insert(TramoHorarioRequest tramoHorarioRequest) {
        TramoHorario tramoHorario = TramoHorario.builder()
                .diaSemana(tramoHorarioRequest.diaSemana())
                .horaInicio(tramoHorarioRequest.horaInicio())
                .horaFin(tramoHorarioRequest.horaFin())
                .tipoTramo(tramoHorarioRequest.tipoTramo())
                .aula(null)
                .build();
        TramoHorario savedTramoHorario = tramoHorarioRepository.save(tramoHorario);
        return Optional.of(savedTramoHorario);
    }

    public void deleteById(Long id) {
        tramoHorarioRepository.deleteById(id);
    }

    public Optional<TramoHorario> update(Long id, TramoHorarioRequest tramoHorarioRequest) {
        return tramoHorarioRepository.findById(id).map(existingTramoHorario -> {
            existingTramoHorario.setDiaSemana(tramoHorarioRequest.diaSemana());
            existingTramoHorario.setHoraInicio(tramoHorarioRequest.horaInicio());
            existingTramoHorario.setHoraFin(tramoHorarioRequest.horaFin());
            existingTramoHorario.setTipoTramo(tramoHorarioRequest.tipoTramo());

            return tramoHorarioRepository.save(existingTramoHorario);
        });
    }
}

package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.aula.AulaRequest;
import com.example.reservasBoscoMdv.DTO.aula.AulaReservasResponse;
import com.example.reservasBoscoMdv.DTO.aula.AulaResponse;
import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.enums.ErrorType;
import com.example.reservasBoscoMdv.errors.BusinessException;
import com.example.reservasBoscoMdv.repositories.IAulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final IAulaRepository aulaRepository;

    public Optional<AulaResponse> findById(Long id) {
        return aulaRepository.findById(id)
                .map(AulaResponse::fromEntity);
    }

    public Aula findEntityById(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                ErrorType.AULA_NO_ENCONTRADA.getCode(),
                ErrorType.AULA_NO_ENCONTRADA.getMessage())
        );
    }

    public List<AulaResponse> findAll() {
        return aulaRepository.findAll()
                .stream()
                .map(AulaResponse::fromEntity)
                .toList();
    }

    public List<AulaResponse> findAllAulasOrdenador() {
        return aulaRepository.findAll()
                .stream()
                .filter(Aula::isEsAulaOrdenador)
                .map(AulaResponse::fromEntity)
                .toList();
    }

    public List<AulaResponse> findAllAulasNoOrdenador() {
        return aulaRepository.findAll()
                .stream()
                .filter(aula -> !aula.isEsAulaOrdenador())
                .map(AulaResponse::fromEntity)
                .toList();
    }

    public List<AulaResponse> findAulasByNombre(String nombre) {
        return aulaRepository.findAll().stream()
                .filter(aula -> aula.getNombre().equalsIgnoreCase(nombre))
                .map(AulaResponse::fromEntity)
                .toList();
    }

    public List<AulaResponse> findAulaCapacidadMayor(int capacidad) {
        return aulaRepository.findAll().stream()
                .filter(aula -> aula.getCapacidad() > capacidad)
                .map(AulaResponse::fromEntity)
                .toList();
    }

    public AulaReservasResponse findAulasWithReservas(Long id) {
        Aula aula = aulaRepository.findAulaWithReservas(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorType.AULA_NO_ENCONTRADA.getCode(),
                        ErrorType.AULA_NO_ENCONTRADA.getMessage())
                );
        return AulaReservasResponse.fromEntity(aula);
    }

    public Optional<AulaResponse> insert(AulaRequest aulaRequest) {
        Aula aula = Aula.builder()
                .nombre(aulaRequest.nombre())
                .capacidad(aulaRequest.capacidad())
                .esAulaOrdenador(aulaRequest.esAulaOrdenador())
                .numOrdenadores(aulaRequest.numOrdenadores())
                .build();
        Aula savedAula = aulaRepository.save(aula);

        AulaResponse response = AulaResponse.fromEntity(savedAula);

        return Optional.of(response);
    }

    public void deleteById(Long id) {
        aulaRepository.deleteById(id);
    }

    public Optional<AulaResponse> update(Long id, AulaRequest aulaRequest) {
        return aulaRepository.findById(id).map(existingAula -> {
            existingAula.setNombre(aulaRequest.nombre());
            existingAula.setCapacidad(aulaRequest.capacidad());
            existingAula.setEsAulaOrdenador(aulaRequest.esAulaOrdenador());
            existingAula.setNumOrdenadores(aulaRequest.numOrdenadores());

            return AulaResponse.fromEntity(aulaRepository.save(existingAula));
        });
    }
}

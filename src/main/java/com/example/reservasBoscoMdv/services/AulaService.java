package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.aula.AulaRequest;
import com.example.reservasBoscoMdv.DTO.aula.AulaResponse;
import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.repositories.IAulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final IAulaRepository aulaRepository;

    public Optional<Aula> findById(Long id) {
        return aulaRepository.findById(id);
    }

    public List<Aula> findAll() {
        return aulaRepository.findAll();
    }

    public List<Aula> findAllAulasOrdenador() {
        return aulaRepository.findAll().stream()
                .filter(Aula::isEsAulaOrdenador)
                .toList();
    }

    public List<Aula> findAllAulasNoOrdenador() {
        return aulaRepository.findAll().stream()
                .filter(aula -> !aula.isEsAulaOrdenador())
                .toList();
    }

    public List<Aula> findAulasByNombre(String nombre) {
        return aulaRepository.findAll().stream()
                .filter(aula -> aula.getNombre().equalsIgnoreCase(nombre))
                .toList();
    }

    public Optional<AulaResponse> insert(AulaRequest aulaRequest) {
        Aula aula = Aula.builder()
                .nombre(aulaRequest.nombre())
                .capacidad(aulaRequest.capacidad())
                .esAulaOrdenador(aulaRequest.esAulaOrdenador())
                .numOrdenadores(aulaRequest.numOrdenadores())
                .build();
        Aula savedAula = aulaRepository.save(aula);

        AulaResponse aulaResponse = new AulaResponse(
                savedAula.getId(),
                savedAula.getNombre(),
                savedAula.getCapacidad(),
                savedAula.isEsAulaOrdenador(),
                savedAula.getNumOrdenadores()
        );

        return Optional.of(aulaResponse);
    }

    public void deleteById(Long id) {
        aulaRepository.deleteById(id);
    }

    public Optional<Aula> update(Long id, AulaRequest aulaRequest) {
        return aulaRepository.findById(id).map(existingAula -> {
            existingAula.setNombre(aulaRequest.nombre());
            existingAula.setCapacidad(aulaRequest.capacidad());
            existingAula.setEsAulaOrdenador(aulaRequest.esAulaOrdenador());
            existingAula.setNumOrdenadores(aulaRequest.numOrdenadores());

            return aulaRepository.save(existingAula);
        });
    }
}

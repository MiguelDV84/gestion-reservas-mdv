package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioRequest;
import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioResponse;
import com.example.reservasBoscoMdv.services.TramoHorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tramo-horario")
@CrossOrigin(origins = "*")
public class TramoHorarioController {

    private final TramoHorarioService tramoHorarioService;

    @PostMapping("/insert")
    public ResponseEntity<TramoHorarioResponse> insertTramoHorario(@Valid @RequestBody TramoHorarioRequest tramoHorarioRequest) {
        return tramoHorarioService.insert(tramoHorarioRequest)
                .map(tramoHorario -> ResponseEntity.ok().body(tramoHorario))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<TramoHorarioResponse>> listAll() {
        return ResponseEntity.ok().body(tramoHorarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramoHorarioResponse> getById(@Valid @PathVariable Long id) {
        return tramoHorarioService.findById(id)
                .map(tramoHorario -> ResponseEntity.ok().body(tramoHorario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        tramoHorarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TramoHorarioResponse> updateTramoHorario(@Valid @PathVariable Long id, @Valid @RequestBody TramoHorarioRequest tramoHorarioRequest) {
        return tramoHorarioService.update(id, tramoHorarioRequest)
                .map(updatedTramoHorario -> ResponseEntity.ok().body(updatedTramoHorario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

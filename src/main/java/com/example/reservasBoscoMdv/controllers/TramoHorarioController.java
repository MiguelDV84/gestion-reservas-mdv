package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioRequest;
import com.example.reservasBoscoMdv.entities.TramoHorario;
import com.example.reservasBoscoMdv.services.TramoHorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tramo-horario")
public class TramoHorarioController {

    private final TramoHorarioService tramoHorarioService;

    @PostMapping("/insert")
    public ResponseEntity<TramoHorario> insertTramoHorario(@RequestBody TramoHorarioRequest tramoHorarioRequest) {
        return tramoHorarioService.insert(tramoHorarioRequest)
                .map(tramoHorario -> ResponseEntity.ok().body(tramoHorario))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<TramoHorario>> listAll() {
        return ResponseEntity.ok().body(tramoHorarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramoHorario> getById(@PathVariable Long id) {
        return tramoHorarioService.findById(id)
                .map(tramoHorario -> ResponseEntity.ok().body(tramoHorario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        tramoHorarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TramoHorario> updateTramoHorario(@PathVariable Long id, @RequestBody TramoHorarioRequest tramoHorarioRequest) {
        return tramoHorarioService.update(id, tramoHorarioRequest)
                .map(updatedTramoHorario -> ResponseEntity.ok().body(updatedTramoHorario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

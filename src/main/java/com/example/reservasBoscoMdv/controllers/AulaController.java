package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.DTO.aula.AulaRequest;
import com.example.reservasBoscoMdv.DTO.aula.AulaReservasResponse;
import com.example.reservasBoscoMdv.DTO.aula.AulaResponse;
import com.example.reservasBoscoMdv.services.AulaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aula")
@CrossOrigin(origins = "*")
public class AulaController {

    private final AulaService aulaService;

    @PostMapping("/insert")
    public ResponseEntity<AulaResponse> insert(@Valid @RequestBody AulaRequest aulaRequest) {
        return aulaService.insert(aulaRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AulaResponse> update(@PathVariable Long id, @Valid @RequestBody AulaRequest aulaRequest) {
        return aulaService.update(id, aulaRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<AulaResponse>> listAll() {
        List<AulaResponse> response = aulaService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponse> getById(@Valid @PathVariable Long id) {
        return aulaService.findById(id)
                .map(aula -> ResponseEntity.ok().body(aula))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/with-reservas/{id}")
    public ResponseEntity<AulaReservasResponse> getAulaWithReservas(@Valid @PathVariable Long id) {
        AulaReservasResponse response = aulaService.findAulasWithReservas(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list/{nombre}")
    public ResponseEntity<Iterable<AulaResponse>> getByNombre(@Valid @PathVariable String nombre) {
        List<AulaResponse> response = aulaService.findAulasByNombre(nombre);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list/ordenadores")
    public ResponseEntity<Iterable<AulaResponse>> listAulasOrdenadores() {
        List<AulaResponse> response = aulaService.findAllAulasOrdenador();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list/no-ordenadores")
    public ResponseEntity<Iterable<AulaResponse>> listAulasNoOrdenadores() {
        List<AulaResponse> response = aulaService.findAllAulasNoOrdenador();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list/capacidad/{capacidad}")
    public ResponseEntity<Iterable<AulaResponse>> getAulasCapacidadMayor(@Valid @PathVariable int capacidad) {
        List<AulaResponse> response = aulaService.findAulaCapacidadMayor(capacidad);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        aulaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

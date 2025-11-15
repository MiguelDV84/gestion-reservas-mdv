package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.DTO.aula.AulaRequest;
import com.example.reservasBoscoMdv.DTO.aula.AulaResponse;
import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.services.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aula")
@CrossOrigin(origins = "*")
public class AulaController {

    private final AulaService aulaService;

    @PostMapping("/insert")
    public ResponseEntity<AulaResponse> insert(@RequestBody AulaRequest aulaRequest) {
        return aulaService.insert(aulaRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Aula>> listAll() {
        return ResponseEntity.ok().body(aulaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aula> getById(@PathVariable Long id) {
        return aulaService.findById(id)
                .map(aula -> ResponseEntity.ok().body(aula))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list/{nombre}")
    public ResponseEntity<Iterable<Aula>> getByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok().body(aulaService.findAulasByNombre(nombre));
    }

    @GetMapping("/list/ordenadores")
    public ResponseEntity<Iterable<Aula>> listAulasOrdenadores() {
        return ResponseEntity.ok().body(aulaService.findAllAulasOrdenador());
    }

    @GetMapping("/list/no-ordenadores")
    public ResponseEntity<Iterable<Aula>> listAulasNoOrdenadores() {
        return ResponseEntity.ok().body(aulaService.findAllAulasNoOrdenador());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        aulaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Aula> update(@PathVariable Long id, @RequestBody AulaRequest aulaRequest) {
        return aulaService.update(id, aulaRequest)
                .map(updatedAula -> ResponseEntity.ok().body(updatedAula))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

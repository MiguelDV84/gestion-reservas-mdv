package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.DTO.reserva.ReservaRequest;
import com.example.reservasBoscoMdv.DTO.reserva.ReservaResponse;
import com.example.reservasBoscoMdv.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserva")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<ReservaResponse>> list() {
        Iterable<ReservaResponse> responses = reservaService.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> getById(@PathVariable Long id) {
        ReservaResponse response = reservaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert")
    public ResponseEntity<ReservaResponse> insert(@RequestBody ReservaRequest reservaRequest) {
        ReservaResponse response = reservaService.insert(reservaRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservaResponse> update(@PathVariable Long id, @RequestBody ReservaRequest reservaRequest) {
        ReservaResponse response = reservaService.update(id, reservaRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.DTO.reserva.ReservaRequest;
import com.example.reservasBoscoMdv.DTO.reserva.ReservaResponse;
import com.example.reservasBoscoMdv.entities.Reserva;
import com.example.reservasBoscoMdv.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping("/insert")
    public ResponseEntity<ReservaResponse> insert(@RequestBody ReservaRequest reservaRequest) {
        return reservaService.insert(reservaRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}

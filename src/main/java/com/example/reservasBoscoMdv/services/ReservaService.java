package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.aula.AulaResponse;
import com.example.reservasBoscoMdv.DTO.reserva.ReservaRequest;
import com.example.reservasBoscoMdv.DTO.reserva.ReservaResponse;
import com.example.reservasBoscoMdv.DTO.tramoHorario.TramoHorarioResponse;
import com.example.reservasBoscoMdv.DTO.usuario.UsuarioResponse;
import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.entities.Reserva;
import com.example.reservasBoscoMdv.entities.TramoHorario;
import com.example.reservasBoscoMdv.entities.Usuario;
import com.example.reservasBoscoMdv.repositories.IReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final IReservaRepository reservaRepository;
    private final AulaService aulaService;
    private final TramoHorarioService tramoHorarioService;
    private final UsuarioService usuarioService;

    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    public Optional<ReservaResponse> insert(ReservaRequest reservaRequest) {
        Aula aula = aulaService.findById(reservaRequest.aulaId()).orElse(null);
        TramoHorario tramoHorario = tramoHorarioService.findById(reservaRequest.tramoId()).orElse(null);
        Usuario usuario = usuarioService.findById(reservaRequest.usuarioId()).orElse(null);
        Reserva reserva = Reserva.builder()
                .motivo(reservaRequest.motivo())
                .numAsistentes(reservaRequest.numAsistentes())
                .aula(aula) // Asignar el aula correspondiente
                .tramoHorario(tramoHorario) // Asignar el tramo horario correspondiente
                .usuario(usuario) // Asignar el usuario correspondiente
                .build();
        Reserva savedReserva = reservaRepository.save(reserva);

        ReservaResponse reservaResponse = getReservaResponse(aula, tramoHorario, usuario, savedReserva);
        return Optional.of(reservaResponse);
    }


    private static ReservaResponse getReservaResponse(Aula aula, TramoHorario tramoHorario, Usuario usuario,Reserva savedReserva) {
        TramoHorarioResponse tramoResponse = new TramoHorarioResponse(
                tramoHorario.getId(),
                tramoHorario.getDiaSemana(),
                tramoHorario.getHoraInicio(),
                tramoHorario.getHoraFin(),
                tramoHorario.getTipoTramo()
        );

        AulaResponse aulaResponse = new AulaResponse(
                aula.getId(),
                aula.getNombre(),
                aula.getCapacidad(),
                aula.isEsAulaOrdenador(),
                aula.getNumOrdenadores()
        );

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );

        return new ReservaResponse(
                savedReserva.getId(),
                savedReserva.getMotivo(),
                savedReserva.getNumAsistentes(),
                savedReserva.getFechaCreacion(),
                aulaResponse,
                tramoResponse,
                usuarioResponse
        );
    }
}

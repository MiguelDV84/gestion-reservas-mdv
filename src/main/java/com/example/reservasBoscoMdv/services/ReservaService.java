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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final IReservaRepository reservaRepository;
    private final AulaService aulaService;
    private final TramoHorarioService tramoHorarioService;
    private final UsuarioService usuarioService;

    public ReservaResponse findById(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Aula aula = reserva.getAula();
        TramoHorario tramo = reserva.getTramoHorario();
        Usuario usuario = reserva.getUsuario();

        return getReservaResponse(aula, tramo, usuario, reserva);
    }

    public List<ReservaResponse> findAll() {
        List<Reserva> reservas = reservaRepository.findAll();

        return reservas.stream().map(reserva -> {
            Aula aula = reserva.getAula();
            TramoHorario tramo = reserva.getTramoHorario();
            Usuario usuario = reserva.getUsuario();
            return getReservaResponse(aula, tramo, usuario, reserva);
        }).toList();
    }

    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }


    public ReservaResponse update(Long id, ReservaRequest request) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Aula aula = aulaService.findById(request.aulaId())
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        TramoHorario tramo = tramoHorarioService.findById(request.tramoId())
                .orElseThrow(() -> new RuntimeException("Tramo horario no encontrado"));

        Usuario usuario = usuarioService.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        reserva.setMotivo(request.motivo());
        reserva.setNumAsistentes(request.numAsistentes());
        reserva.setAula(aula);
        reserva.setTramoHorario(tramo);
        reserva.setUsuario(usuario);

        Reserva reservaUpdated = reservaRepository.save(reserva);

        return getReservaResponse(aula, tramo, usuario, reservaUpdated);
    }

    public ReservaResponse insert(ReservaRequest request) {

        Aula aula = aulaService.findById(request.aulaId())
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        TramoHorario tramo = tramoHorarioService.findById(request.tramoId())
                .orElseThrow(() -> new RuntimeException("Tramo horario no encontrado"));

        Usuario usuario = usuarioService.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Reserva reserva = Reserva.builder()
                .motivo(request.motivo())
                .numAsistentes(request.numAsistentes())
                .aula(aula)
                .tramoHorario(tramo)
                .usuario(usuario)
                .build();

        Reserva saved = reservaRepository.save(reserva);

        return getReservaResponse(aula, tramo, usuario, saved);
    }

    private ReservaResponse getReservaResponse(Aula aula, TramoHorario tramoHorario, Usuario usuario, Reserva savedReserva) {
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

package com.example.reservasBoscoMdv.config;

import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.entities.TramoHorario;
import com.example.reservasBoscoMdv.enums.DiaSemana;
import com.example.reservasBoscoMdv.enums.TipoTramo;
import com.example.reservasBoscoMdv.repositories.IAulaRepository;
import com.example.reservasBoscoMdv.repositories.ITramoHorarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final IAulaRepository aulaRepository;
    private final ITramoHorarioRepository tramoHorarioRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (aulaRepository.count() == 0) {
                log.info("Insertando aulas de ejemplo...");
                
                aulaRepository.save(Aula.builder()
                        .nombre("Aula 101")
                        .capacidad(30)
                        .esAulaOrdenador(true)
                        .numOrdenadores(25)
                        .build());

                aulaRepository.save(Aula.builder()
                        .nombre("Aula 102")
                        .capacidad(25)
                        .esAulaOrdenador(false)
                        .numOrdenadores(0)
                        .build());

                aulaRepository.save(Aula.builder()
                        .nombre("Aula Informatica 1")
                        .capacidad(35)
                        .esAulaOrdenador(true)
                        .numOrdenadores(30)
                        .build());

                aulaRepository.save(Aula.builder()
                        .nombre("Aula Magna")
                        .capacidad(100)
                        .esAulaOrdenador(false)
                        .numOrdenadores(0)
                        .build());

                aulaRepository.save(Aula.builder()
                        .nombre("Laboratorio")
                        .capacidad(20)
                        .esAulaOrdenador(true)
                        .numOrdenadores(15)
                        .build());

                log.info("5 aulas insertadas");
            }

            if (tramoHorarioRepository.count() == 0) {
                log.info("Insertando tramos horarios...");

                // Lunes
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.LUNES).horaInicio(LocalTime.of(8, 0))
                        .horaFin(LocalTime.of(9, 0)).tipoTramo(TipoTramo.LECTIVO).build());
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.LUNES).horaInicio(LocalTime.of(9, 0))
                        .horaFin(LocalTime.of(10, 0)).tipoTramo(TipoTramo.LECTIVO).build());
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.LUNES).horaInicio(LocalTime.of(10, 0))
                        .horaFin(LocalTime.of(10, 30)).tipoTramo(TipoTramo.RECREO).build());
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.LUNES).horaInicio(LocalTime.of(10, 30))
                        .horaFin(LocalTime.of(11, 30)).tipoTramo(TipoTramo.LECTIVO).build());

                // Martes
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.MARTES).horaInicio(LocalTime.of(8, 0))
                        .horaFin(LocalTime.of(9, 0)).tipoTramo(TipoTramo.LECTIVO).build());
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.MARTES).horaInicio(LocalTime.of(9, 0))
                        .horaFin(LocalTime.of(10, 0)).tipoTramo(TipoTramo.LECTIVO).build());

                // Miércoles
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.MIERCOLES).horaInicio(LocalTime.of(8, 0))
                        .horaFin(LocalTime.of(9, 0)).tipoTramo(TipoTramo.LECTIVO).build());

                // Jueves
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.JUEVES).horaInicio(LocalTime.of(8, 0))
                        .horaFin(LocalTime.of(9, 0)).tipoTramo(TipoTramo.LECTIVO).build());

                // Viernes
                tramoHorarioRepository.save(TramoHorario.builder()
                        .diaSemana(DiaSemana.VIERNES).horaInicio(LocalTime.of(8, 0))
                        .horaFin(LocalTime.of(9, 0)).tipoTramo(TipoTramo.LECTIVO).build());

                log.info("9 tramos horarios insertados");
            }

            log.info("Base de datos inicializada correctamente");
        };
    }
}

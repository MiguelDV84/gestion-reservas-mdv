package com.example.reservasBoscoMdv.repositories;

import com.example.reservasBoscoMdv.entities.Aula;
import com.example.reservasBoscoMdv.entities.Reserva;
import com.example.reservasBoscoMdv.entities.TramoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("""
                SELECT COUNT(r) > 0
                FROM Reserva r
                WHERE r.fechaReserva = :fechaReserva
                  AND r.aula.id = :aulaId
                  AND r.tramoHorario.id = :tramoId
            """)
    boolean existsByAulaAndFechaReservaAndTramoHorario(@Param("aulaId") Long aulaId,
                                                       @Param("fechaReserva") LocalDate fechaReserva,
                                                       @Param("tramoId") Long tramoHorarioId);

}

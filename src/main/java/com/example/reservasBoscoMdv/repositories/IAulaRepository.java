package com.example.reservasBoscoMdv.repositories;

import com.example.reservasBoscoMdv.entities.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAulaRepository extends JpaRepository<Aula, Long> {
    @Query("""
                SELECT a FROM Aula a
                JOIN FETCH a.reservas r
                WHERE a.id = :id
            """)
    Optional<Aula> findAulaWithReservas(@Param("id") Long id);
}

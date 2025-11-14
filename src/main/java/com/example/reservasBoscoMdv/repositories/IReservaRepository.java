package com.example.reservasBoscoMdv.repositories;

import com.example.reservasBoscoMdv.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
}

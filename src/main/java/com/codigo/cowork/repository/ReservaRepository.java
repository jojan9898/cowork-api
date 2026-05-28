package com.codigo.cowork.repository;

import com.codigo.cowork.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public List<Reserva> findAll() {
        return reservas;
    }

    public Optional<Reserva> findById(Long id) {
        return reservas.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public List<Reserva> findBySalaId(Long salaId) {
        return reservas.stream()
                .filter(r -> r.getSalaId().equals(salaId))
                .collect(Collectors.toList());
    }

    public Reserva save(Reserva reserva) {
        reserva.setId(contador.getAndIncrement());
        reservas.add(reserva);
        return reserva;
    }

    public Optional<Reserva> update(Long id, Reserva datos) {
        return findById(id).map(reserva -> {
            reserva.setSalaId(datos.getSalaId());
            reserva.setResponsable(datos.getResponsable());
            reserva.setEmail(datos.getEmail());
            reserva.setFecha(datos.getFecha());
            reserva.setHoraInicio(datos.getHoraInicio());
            reserva.setHoraFin(datos.getHoraFin());
            reserva.setEstado(datos.getEstado());
            return reserva;
        });
    }

    public boolean delete(Long id) {
        return reservas.removeIf(r -> r.getId().equals(id));
    }

    public void deleteBySalaId(Long salaId) {
        reservas.removeIf(r -> r.getSalaId().equals(salaId));
    }
}

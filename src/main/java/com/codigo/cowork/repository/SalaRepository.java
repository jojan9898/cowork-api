package com.codigo.cowork.repository;

import com.codigo.cowork.model.Sala;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SalaRepository {

    private final List<Sala> salas = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public List<Sala> findAll() {
        return salas;
    }

    public Optional<Sala> findById(Long id) {
        return salas.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public Sala save(Sala sala) {
        sala.setId(contador.getAndIncrement());
        salas.add(sala);
        return sala;
    }

    public Optional<Sala> update(Long id, Sala datos) {
        return findById(id).map(sala -> {
            sala.setCodigo(datos.getCodigo());
            sala.setNombre(datos.getNombre());
            sala.setCapacidad(datos.getCapacidad());
            sala.setUbicacion(datos.getUbicacion());
            sala.setActiva(datos.isActiva());
            return sala;
        });
    }

    public boolean delete(Long id) {
        return salas.removeIf(s -> s.getId().equals(id));
    }
}

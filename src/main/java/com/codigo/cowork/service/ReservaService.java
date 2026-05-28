package com.codigo.cowork.service;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.mapper.ReservaMapper;
import com.codigo.cowork.model.Reserva;
import com.codigo.cowork.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        Reserva reserva = ReservaMapper.toModel(dto);
        reserva.setEstado("PENDIENTE");
        return ReservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }

    public Optional<ReservaResponseDTO> buscarPorId(Long id) {
        return reservaRepository.findById(id).map(ReservaMapper::toResponseDTO);
    }

    public List<ReservaResponseDTO> listarConFiltros(String estado, String fecha, Long salaId) {
        return reservaRepository.findAll().stream()
                .filter(r -> estado == null || r.getEstado().equalsIgnoreCase(estado))
                .filter(r -> fecha == null || r.getFecha().equals(LocalDate.parse(fecha)))
                .filter(r -> salaId == null || r.getSalaId().equals(salaId))
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> listarPorSala(Long salaId) {
        return reservaRepository.findBySalaId(salaId).stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservaResponseDTO> cambiarEstado(Long id, String nuevoEstado) {
        if (!nuevoEstado.equals("PENDIENTE") && !nuevoEstado.equals("CONFIRMADA") && !nuevoEstado.equals("CANCELADA")) {
            throw new RuntimeException("Estado no valido: " + nuevoEstado + ". Solo se acepta PENDIENTE, CONFIRMADA o CANCELADA.");
        }
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setEstado(nuevoEstado);
            return ReservaMapper.toResponseDTO(reserva);
        });
    }

    public boolean eliminar(Long id) {
        return reservaRepository.delete(id);
    }
}

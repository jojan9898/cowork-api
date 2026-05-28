package com.codigo.cowork.service;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.mapper.SalaMapper;
import com.codigo.cowork.model.Sala;
import com.codigo.cowork.repository.ReservaRepository;
import com.codigo.cowork.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public SalaService(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<SalaResponseDTO> listarTodas() {
        return salaRepository.findAll().stream()
                .map(SalaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<SalaResponseDTO> buscarPorId(Long id) {
        return salaRepository.findById(id).map(SalaMapper::toResponseDTO);
    }

    public SalaResponseDTO crear(SalaRequestDTO dto) {
        Sala sala = SalaMapper.toModel(dto);
        sala.setActiva(true);
        return SalaMapper.toResponseDTO(salaRepository.save(sala));
    }

    public Optional<SalaResponseDTO> actualizar(Long id, SalaRequestDTO dto) {
        Sala datos = SalaMapper.toModel(dto);
        datos.setActiva(true);
        return salaRepository.update(id, datos).map(SalaMapper::toResponseDTO);
    }

    public boolean eliminar(Long id) {
        boolean eliminada = salaRepository.delete(id);
        if (eliminada) {
            reservaRepository.deleteBySalaId(id);
        }
        return eliminada;
    }
}

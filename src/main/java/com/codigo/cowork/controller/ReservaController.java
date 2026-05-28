package com.codigo.cowork.controller;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(201).body(reservaService.crear(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscar(@PathVariable Long id) {
        return reservaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ReservaResponseDTO> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) Long salaId) {
        return reservaService.listarConFiltros(estado, fecha, salaId);
    }

    @GetMapping("/sala/{salaId}")
    public List<ReservaResponseDTO> listarPorSala(@PathVariable Long salaId) {
        return reservaService.listarPorSala(salaId);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return reservaService.cambiarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (reservaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/comprobante")
    public ResponseEntity<Map<String, Object>> subirComprobante(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestHeader("X-Cliente-Id") String clienteId) {
        return ResponseEntity.ok(Map.of(
                "nombre", archivo.getOriginalFilename(),
                "tamano", archivo.getSize()
        ));
    }
}

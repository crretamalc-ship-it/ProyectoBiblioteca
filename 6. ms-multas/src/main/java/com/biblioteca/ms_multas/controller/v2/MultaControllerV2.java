package com.biblioteca.ms_multas.controller.v2;

import com.biblioteca.ms_multas.assemblers.MultaModelAssembler;
import com.biblioteca.ms_multas.dto.MultaAjusteDTO;
import com.biblioteca.ms_multas.model.Multa;
import com.biblioteca.ms_multas.service.MultaService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/multas")
public class MultaControllerV2 {

    private final MultaService service;
    private final MultaModelAssembler assembler;

    public MultaControllerV2(MultaService service, MultaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping("/calcular/{prestamoId}")
    public ResponseEntity<EntityModel<Multa>> calcular(@PathVariable Long prestamoId) {
        Multa multa = service.calcularMulta(prestamoId);
        return ResponseEntity.status(201).body(assembler.toModel(multa));
    }

    @PostMapping("/pagar/{multaId}")
    public ResponseEntity<EntityModel<Multa>> pagar(@PathVariable Long multaId) {
        return ResponseEntity.ok(assembler.toModel(service.pagarMulta(multaId)));
    }

    @PutMapping("/ajustar/{multaId}")
    public ResponseEntity<EntityModel<Multa>> ajustar(@PathVariable Long multaId,
                                                      @Valid @RequestBody MultaAjusteDTO dto) {
        Multa multa = service.ajustarMulta(multaId, dto.getDiasRetraso(), dto.getMonto());
        return ResponseEntity.ok(assembler.toModel(multa));
    }

    @PutMapping("/anular/{multaId}")
    public ResponseEntity<EntityModel<Multa>> anular(@PathVariable Long multaId) {
        return ResponseEntity.ok(assembler.toModel(service.anularMulta(multaId)));
    }

    @DeleteMapping("/{multaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long multaId) {
        service.eliminarMulta(multaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<CollectionModel<EntityModel<Multa>>> listarPorUsuario(@PathVariable String email) {
        List<EntityModel<Multa>> multas = service.listarPorUsuario(email).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(multas));
    }

    @GetMapping("/pendientes/{email}")
    public ResponseEntity<EntityModel<Map<String, Object>>> tienePendientes(@PathVariable String email) {
        boolean pendientes = service.tienePendientes(email);
        long cantidad = service.listarPendientesPorUsuario(email).size();
        return ResponseEntity.ok(EntityModel.of(Map.of(
                "email", email,
                "tienePendientes", pendientes,
                "cantidad", cantidad
        )));
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<EntityModel<Multa>> verUna(@PathVariable Long id) {
        Multa multa = service.buscarPorId(id);
        if (multa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(multa));
    }

    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Multa>>> listarTodas() {
        List<EntityModel<Multa>> multas = service.listarTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(multas));
    }
}

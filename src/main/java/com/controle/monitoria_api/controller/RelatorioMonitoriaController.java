package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.RelatorioMonitoriaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.RelatorioMonitoriaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.RelatorioMonitoriaResponseDTO;
import com.controle.monitoria_api.service.RelatorioMonitoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/relatorios-monitoria")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RelatorioMonitoriaController {

    private final RelatorioMonitoriaService service;

    @PostMapping
    public ResponseEntity<RelatorioMonitoriaResponseDTO> criar(@Valid @RequestBody RelatorioMonitoriaCriacaoDTO dto) {
        var relatorio = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(relatorio.id())
                .toUri();

        return ResponseEntity.created(uri).body(relatorio);
    }

    @GetMapping
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarTodos(paginacao));
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarPorProfessor(
            @PathVariable Long professorId,
            @PageableDefault(size = 10, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorProfessor(professorId, paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioMonitoriaResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @GetMapping("/monitoria/{monitoriaId}")
    public ResponseEntity<RelatorioMonitoriaResponseDTO> listarPorMonitoria(@PathVariable Long monitoriaId) {
        return ResponseEntity.ok(service.listarPorMonitoria(monitoriaId));
    }

    @PutMapping
    public ResponseEntity<RelatorioMonitoriaResponseDTO> atualizar(@Valid @RequestBody RelatorioMonitoriaAtualizacaoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.relatorioMonitoria.RelatorioMonitoriaCriacaoDTO;
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
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarPorProfessor(@PathVariable Long professorId, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorProfessor(professorId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarPorDisciplina(@PathVariable Long disciplinaId, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorDisciplina(disciplinaId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarPorAluno(@PathVariable Long alunoId, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorAluno(alunoId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/semestre/{semestre}")
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarPorSemestre(@PathVariable String semestre, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorSemestre(semestre, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/status-monitoria/{status}")
    public ResponseEntity<Page<RelatorioMonitoriaResponseDTO>> listarPorStatusMonitoria(@PathVariable String status, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorStatusMonitoria(status, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/monitoria/{monitoriaId}")
    public ResponseEntity<RelatorioMonitoriaResponseDTO> listarPorMonitoria(@PathVariable Long monitoriaId) {
        var relatorio = service.listarPorMonitoria(monitoriaId);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioMonitoriaResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }
}
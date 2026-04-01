package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.MonitoriaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.MonitoriaCriacaoDTO;
import com.controle.monitoria_api.model.dto.request.MonitoriaFinalizacaoDTO;
import com.controle.monitoria_api.model.dto.response.MonitoriaResponseDTO;
import com.controle.monitoria_api.service.MonitoriaService;
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
@RequestMapping("/monitorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MonitoriaController {

    private final MonitoriaService service;

    @PostMapping
    public ResponseEntity<MonitoriaResponseDTO> criar(@Valid @RequestBody MonitoriaCriacaoDTO dto) {
        var monitoria = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(monitoria.id())
                .toUri();

        return ResponseEntity.created(uri).body(monitoria);
    }

    @GetMapping
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarTodos(paginacao));
    }

    @GetMapping("/em-andamento")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarEmAndamento(
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarEmAndamento(paginacao));
    }

    @GetMapping("/finalizadas")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarFinalizadas(
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarFinalizadas(paginacao));
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorProfessor(
            @PathVariable Long professorId,
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorProfessor(professorId, paginacao));
    }

    @GetMapping("/professor/{professorId}/em-andamento")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorProfessorEmAndamento(
            @PathVariable Long professorId,
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorProfessorEmAndamento(professorId, paginacao));
    }

    @GetMapping("/professor/{professorId}/finalizadas")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorProfessorFinalizadas(
            @PathVariable Long professorId,
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorProfessorFinalizadas(professorId, paginacao));
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorDisciplina(
            @PathVariable Long disciplinaId,
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorDisciplina(disciplinaId, paginacao));
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorAluno(
            @PathVariable Long alunoId,
            @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorAluno(alunoId, paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitoriaResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PutMapping
    public ResponseEntity<MonitoriaResponseDTO> atualizar(@Valid @RequestBody MonitoriaAtualizacaoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PatchMapping("/finalizar")
    public ResponseEntity<MonitoriaResponseDTO> finalizar(@Valid @RequestBody MonitoriaFinalizacaoDTO dto) {
        return ResponseEntity.ok(service.finalizar(dto));
    }

    @GetMapping("/disciplina/{disciplinaId}/semestre/{semestre}/quantitativo")
    public ResponseEntity<Long> quantitativoPorDisciplinaESemestre(
            @PathVariable Long disciplinaId,
            @PathVariable String semestre) {
        return ResponseEntity.ok(service.contarPorDisciplinaESemestre(disciplinaId, semestre));
    }
}
package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.AlunoAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.AlunoCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.AlunoResponseDTO;
import com.controle.monitoria_api.service.AlunoService;
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
@RequestMapping("/alunos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AlunoService service;

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criar(@Valid @RequestBody AlunoCriacaoDTO dto) {
        var aluno = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aluno.id())
                .toUri();

        return ResponseEntity.created(uri).body(aluno);
    }

    @GetMapping
    public ResponseEntity<Page<AlunoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarTodos(paginacao));
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<AlunoResponseDTO>> listarAtivos(
            @PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarAtivos(paginacao));
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<AlunoResponseDTO>> listarInativos(
            @PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarInativos(paginacao));
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<Page<AlunoResponseDTO>> listarPorProfessor(
            @PathVariable Long professorId,
            @PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorProfessor(professorId, paginacao));
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<Page<AlunoResponseDTO>> listarPorDisciplina(
            @PathVariable Long disciplinaId,
            @PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorDisciplina(disciplinaId, paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PutMapping
    public ResponseEntity<AlunoResponseDTO> atualizar(@Valid @RequestBody AlunoAtualizacaoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }
}
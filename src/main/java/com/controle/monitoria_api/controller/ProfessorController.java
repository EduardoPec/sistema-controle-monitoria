package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.professor.ProfessorAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.professor.ProfessorCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.ProfessorResponseDTO;
import com.controle.monitoria_api.service.ProfessorService;
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
@RequestMapping("/professores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProfessorController {

    private final ProfessorService service;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> criar(@Valid @RequestBody ProfessorCriacaoDTO dto) {
        var professor = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(professor.id())
                .toUri();

        return ResponseEntity.created(uri).body(professor);
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<ProfessorResponseDTO>> listarAtivos(@PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarAtivos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<ProfessorResponseDTO>> listarInativos(@PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarInativos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/escola/{escolaId}")
    public ResponseEntity<Page<ProfessorResponseDTO>> listarPorEscola(@PathVariable Long escolaId, @PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorEscola(escolaId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> listarPorId(@PathVariable Long id) {
        var professor = service.listarPorId(id);
        return ResponseEntity.ok(professor);
    }

    @PutMapping
    public ResponseEntity<ProfessorResponseDTO> atualizar(@RequestBody @Valid ProfessorAtualizacaoDTO dto) {
        var professor = service.atualizar(dto);
        return ResponseEntity.ok(professor);
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

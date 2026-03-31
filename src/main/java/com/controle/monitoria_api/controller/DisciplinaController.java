package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.DisciplinaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.DisciplinaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.DisciplinaResponseDTO;
import com.controle.monitoria_api.service.DisciplinaService;
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
@RequestMapping("/disciplinas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DisciplinaController {

    private final DisciplinaService service;

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> criar(@Valid @RequestBody DisciplinaCriacaoDTO dto) {
        var disciplina = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(disciplina.id())
                .toUri();

        return ResponseEntity.created(uri).body(disciplina);
    }

    @GetMapping
    public ResponseEntity<Page<DisciplinaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<DisciplinaResponseDTO>> listarAtivos(
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarAtivos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<DisciplinaResponseDTO>> listarInativos(
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarInativos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/escola/{escolaId}")
    public ResponseEntity<Page<DisciplinaResponseDTO>> listarPorEscola(
            @PathVariable Long escolaId,
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorEscola(escolaId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> listarPorId(@PathVariable Long id) {
        var disciplina = service.buscarPorId(id);
        return ResponseEntity.ok(disciplina);
    }

    @PutMapping
    public ResponseEntity<DisciplinaResponseDTO> atualizar(@Valid @RequestBody DisciplinaAtualizacaoDTO dto) {
        var disciplina = service.atualizar(dto);
        return ResponseEntity.ok(disciplina);
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

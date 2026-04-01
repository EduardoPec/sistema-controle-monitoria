package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.curso.CursoAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.curso.CursoCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.CursoResponseDTO;
import com.controle.monitoria_api.service.CursoService;
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
@RequestMapping("/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService service;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> criar(@Valid @RequestBody CursoCriacaoDTO dto) {
        var curso = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(curso.id())
                .toUri();

        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping
    public ResponseEntity<Page<CursoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<CursoResponseDTO>> listarAtivos(
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarAtivos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<CursoResponseDTO>> listarInativos(
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarInativos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/escola/{escolaId}")
    public ResponseEntity<Page<CursoResponseDTO>> listarPorEscola(
            @PathVariable Long escolaId,
            @PageableDefault(size = 10, sort = {"sigla"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorEscola(escolaId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> listarPorId(@PathVariable Long id) {
        var curso = service.listarPorId(id);
        return ResponseEntity.ok(curso);
    }

    @PutMapping
    public ResponseEntity<CursoResponseDTO> atualizar(@Valid @RequestBody CursoAtualizacaoDTO dto) {
        var curso = service.atualizar(dto);
        return ResponseEntity.ok(curso);
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

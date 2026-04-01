package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.matrizCurricular.MatrizCurricularAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.matrizCurricular.MatrizCurricularCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.MatrizCurricularResponseDTO;
import com.controle.monitoria_api.service.MatrizCurricularService;
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
@RequestMapping("/matrizes-curriculares")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MatrizCurricularController {

    private final MatrizCurricularService service;

    @PostMapping
    public ResponseEntity<MatrizCurricularResponseDTO> criar(@Valid @RequestBody MatrizCurricularCriacaoDTO dto) {
        var matriz = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(matriz.id())
                .toUri();

        return ResponseEntity.created(uri).body(matriz);
    }

    @GetMapping
    public ResponseEntity<Page<MatrizCurricularResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<MatrizCurricularResponseDTO>> listarAtivos(
            @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarAtivos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<MatrizCurricularResponseDTO>> listarInativos(
            @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarInativos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<Page<MatrizCurricularResponseDTO>> listarPorCurso(
            @PathVariable Long cursoId,
            @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorCurso(cursoId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/curso/{cursoId}/ativa")
    public ResponseEntity<MatrizCurricularResponseDTO> buscarMatrizAtivaPorCurso(@PathVariable Long cursoId) {
        var matriz = service.listarMatrizAtivaPorCurso(cursoId);
        return ResponseEntity.ok(matriz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatrizCurricularResponseDTO> buscarPorId(@PathVariable Long id) {
        var matriz = service.listarPorId(id);
        return ResponseEntity.ok(matriz);
    }

    @PutMapping
    public ResponseEntity<MatrizCurricularResponseDTO> atualizar(@Valid @RequestBody MatrizCurricularAtualizacaoDTO dto) {
        var matriz = service.atualizar(dto);
        return ResponseEntity.ok(matriz);
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
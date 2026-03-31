package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.MatrizCurricularAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.MatrizCurricularCriacaoDTO;
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
@RequestMapping("/matrizes")
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
        return ResponseEntity.ok(service.listarTodos(paginacao));
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<MatrizCurricularResponseDTO>> listarAtivos(
            @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarAtivos(paginacao));
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<MatrizCurricularResponseDTO>> listarInativos(
            @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarInativos(paginacao));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<Page<MatrizCurricularResponseDTO>> listarPorCurso(
            @PathVariable Long cursoId,
            @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(service.listarPorCurso(cursoId, paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatrizCurricularResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PutMapping
    public ResponseEntity<MatrizCurricularResponseDTO> atualizar(@Valid @RequestBody MatrizCurricularAtualizacaoDTO dto) {
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
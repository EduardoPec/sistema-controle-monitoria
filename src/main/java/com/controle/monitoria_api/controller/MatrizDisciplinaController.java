package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.matrizDisciplina.MatrizDisciplinaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.matrizDisciplina.MatrizDisciplinaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.MatrizDisciplinaResponseDTO;
import com.controle.monitoria_api.service.MatrizDisciplinaService;
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
@RequestMapping("/matrizes-disciplinas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MatrizDisciplinaController {

    private final MatrizDisciplinaService service;

    @PostMapping
    public ResponseEntity<MatrizDisciplinaResponseDTO> criar(@Valid @RequestBody MatrizDisciplinaCriacaoDTO dto) {
        var associacao = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(associacao.id())
                .toUri();

        return ResponseEntity.created(uri).body(associacao);
    }

    @GetMapping
    public ResponseEntity<Page<MatrizDisciplinaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/matriz/{matrizId}")
    public ResponseEntity<Page<MatrizDisciplinaResponseDTO>> listarPorMatriz(@PathVariable Long matrizId, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorMatriz(matrizId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<Page<MatrizDisciplinaResponseDTO>> listarPorDisciplina(@PathVariable Long disciplinaId, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorDisciplina(disciplinaId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatrizDisciplinaResponseDTO> buscarPorId(@PathVariable Long id) {
        var associacao = service.listarPorId(id);
        return ResponseEntity.ok(associacao);
    }

    @PutMapping
    public ResponseEntity<MatrizDisciplinaResponseDTO> atualizar(@Valid @RequestBody MatrizDisciplinaAtualizacaoDTO dto) {
        var associacao = service.atualizar(dto);
        return ResponseEntity.ok(associacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

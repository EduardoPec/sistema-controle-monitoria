package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.formacao.FormacaoAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.formacao.FormacaoCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.FormacaoResponseDTO;
import com.controle.monitoria_api.service.FormacaoService;
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
@RequestMapping("/formacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FormacaoController {

    private final FormacaoService service;

    @PostMapping
    public ResponseEntity<FormacaoResponseDTO> criar(@Valid @RequestBody FormacaoCriacaoDTO dto) {
        var formacao = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(formacao.id())
                .toUri();

        return ResponseEntity.created(uri).body(formacao);
    }

    @GetMapping
    public ResponseEntity<Page<FormacaoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"titulacao"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<Page<FormacaoResponseDTO>> listarPorProfessor(@PathVariable Long professorId, @PageableDefault(size = 10, sort = {"titulacao"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorProfessor(professorId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormacaoResponseDTO> listarPorId(@PathVariable Long id) {
        var formacao = service.listarPorId(id);
        return ResponseEntity.ok(formacao);
    }

    @PutMapping
    public ResponseEntity<FormacaoResponseDTO> atualizar(@Valid @RequestBody FormacaoAtualizacaoDTO dto) {
        var formacao = service.atualizar(dto);
        return ResponseEntity.ok(formacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
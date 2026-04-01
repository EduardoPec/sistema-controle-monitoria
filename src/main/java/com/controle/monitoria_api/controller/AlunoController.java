package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.aluno.AlunoAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.aluno.AlunoCriacaoDTO;
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
    public ResponseEntity<Page<AlunoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<AlunoResponseDTO>> listarAtivos(@PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarAtivos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<AlunoResponseDTO>> listarInativos(@PageableDefault(size = 10, sort = {"nomeCompleto"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarInativos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> listarPorId(@PathVariable Long id) {
        var aluno = service.listarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @PutMapping
    public ResponseEntity<AlunoResponseDTO> atualizar(@Valid @RequestBody AlunoAtualizacaoDTO dto) {
        var aluno = service.atualizar(dto);
        return ResponseEntity.ok(aluno);
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
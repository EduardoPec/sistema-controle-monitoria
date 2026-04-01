package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.escola.EscolaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.escola.EscolaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.EscolaResponseDTO;
import com.controle.monitoria_api.service.EscolaService;
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
@RequestMapping("/escolas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EscolaController {

    private final EscolaService service;

    @PostMapping
    public ResponseEntity<EscolaResponseDTO> criar(@Valid @RequestBody EscolaCriacaoDTO dto) {
        var escola = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(escola.id())
                .toUri();

        return ResponseEntity.created(uri).body(escola);
    }

    @GetMapping
    public ResponseEntity<Page<EscolaResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<EscolaResponseDTO>> listarAtivos(@PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarAtivos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<EscolaResponseDTO>> listarInativos(@PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarInativos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ies/{iesId}")
    public ResponseEntity<Page<EscolaResponseDTO>> listarPorIES(@PathVariable Long iesId, @PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarPorIES(iesId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscolaResponseDTO> listarPorId(@PathVariable Long id) {
        var escola = service.listarPorId(id);
        return ResponseEntity.ok(escola);
    }

    @PutMapping
    public ResponseEntity<EscolaResponseDTO> atualizar(@RequestBody @Valid EscolaAtualizacaoDTO dto) {
        var escola = service.atualizar(dto);
        return ResponseEntity.ok(escola);
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

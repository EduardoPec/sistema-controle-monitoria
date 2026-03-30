package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.IESAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.IESRequestDTO;
import com.controle.monitoria_api.model.dto.response.IESResponseDTO;
import com.controle.monitoria_api.service.IESService;
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
@RequestMapping("/ies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IESController {

    private final IESService service;

    @PostMapping
    public ResponseEntity<IESResponseDTO> criar(@Valid @RequestBody IESRequestDTO dto) {
        var ies = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ies.id())
                .toUri();

        return ResponseEntity.created(uri).body(ies);
    }

    @GetMapping
    public ResponseEntity<Page<IESResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IESResponseDTO> listarPorId(@PathVariable Long id) {
        var ies = service.listarPorId(id);
        return ResponseEntity.ok(ies);
    }

    @PutMapping
    public ResponseEntity<IESResponseDTO> atualizar(@RequestBody @Valid IESAtualizacaoDTO dto) {
        var ies = service.atualizar(dto);
        return ResponseEntity.ok(ies);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

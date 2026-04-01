package com.controle.monitoria_api.controller;

import com.controle.monitoria_api.model.dto.request.monitoria.MonitoriaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.monitoria.MonitoriaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.MonitoriaResponseDTO;
import com.controle.monitoria_api.service.MonitoriaService;
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
@RequestMapping("/monitorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MonitoriaController {

    private final MonitoriaService service;

    @PostMapping
    public ResponseEntity<MonitoriaResponseDTO> criar(@Valid @RequestBody MonitoriaCriacaoDTO dto) {
        var monitoria = service.criar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(monitoria.id())
                .toUri();

        return ResponseEntity.created(uri).body(monitoria);
    }

    @GetMapping
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var page = service.listarTodos(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorProfessor(@PathVariable Long professorId, @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var page = service.listarPorProfessor(professorId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorAluno(@PathVariable Long alunoId, @PageableDefault(size = 10, sort = {"semestre"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var page = service.listarPorAluno(alunoId, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<MonitoriaResponseDTO>> listarPorStatus(@PathVariable String status, @PageableDefault(size = 10, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        var page = service.listarPorStatus(status, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitoriaResponseDTO> listarPorId(@PathVariable Long id) {
        var monitoria = service.listarPorId(id);
        return ResponseEntity.ok(monitoria);
    }

    @PutMapping
    public ResponseEntity<MonitoriaResponseDTO> atualizar(@Valid @RequestBody MonitoriaAtualizacaoDTO dto) {
        var monitoria = service.atualizar(dto);
        return ResponseEntity.ok(monitoria);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        service.finalizar(id);
        return ResponseEntity.noContent().build();
    }
}
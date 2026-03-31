package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Professor;

import java.time.LocalDateTime;

public record ProfessorResponseDTO(
        Long id,
        String matricula,
        String nomeCompleto,
        String email,
        String telefone,
        EscolaResponseDTO escola,
        LocalDateTime dataCadastro,
        Boolean ativo) {

    public ProfessorResponseDTO(Professor professor) {
        this(professor.getId(), professor.getMatricula(), professor.getNomeCompleto(), professor.getEmail(), professor.getTelefone(), new EscolaResponseDTO(professor.getEscola()), professor.getDataCadastro(), professor.getAtivo());
    }
}

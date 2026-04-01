package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Formacao;

public record FormacaoResponseDTO(
        Long id,
        Long professorId,
        String professorNome,
        String titulacao,
        String instituicao,
        String nomeCurso,
        Integer anoConclusao
) {

    public FormacaoResponseDTO(Formacao formacao) {
        this(formacao.getId(), formacao.getProfessor().getId(), formacao.getProfessor().getNomeCompleto(), formacao.getTitulacao(), formacao.getInstituicao(), formacao.getNomeCurso(), formacao.getAnoConclusao());
    }
}
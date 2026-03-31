package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Curso;

import java.time.LocalDateTime;

public record CursoResponseDTO(
        Long id,
        String sigla,
        String descricao,
        EscolaResponseDTO escola,
        String turno,
        String coordenador,
        LocalDateTime dataCadastro,
        Boolean ativo) {

    public CursoResponseDTO(Curso curso) {
        this(curso.getId(), curso.getSigla(), curso.getDescricao(), new EscolaResponseDTO(curso.getEscola()), curso.getTurno(), curso.getCoordenador(), curso.getDataCadastro(), curso.getAtivo());
    }
}

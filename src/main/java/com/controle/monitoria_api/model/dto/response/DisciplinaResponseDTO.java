package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Disciplina;

import java.time.LocalDateTime;

public record DisciplinaResponseDTO(
        Long id,
        String sigla,
        String descricao,
        Integer cargaHoraria,
        EscolaResponseDTO escola,
        LocalDateTime dataCadastro,
        Boolean ativo) {

    public DisciplinaResponseDTO(Disciplina disciplina) {
        this(disciplina.getId(), disciplina.getSigla(), disciplina.getDescricao(), disciplina.getCargaHoraria(), new EscolaResponseDTO(disciplina.getEscola()), disciplina.getDataCadastro(), disciplina.getAtivo());
    }
}

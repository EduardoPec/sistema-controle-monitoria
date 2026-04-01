package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Aluno;
import java.time.LocalDateTime;

public record AlunoResponseDTO(
        Long id,
        String matricula,
        String nomeCompleto,
        LocalDateTime dataCadastro,
        Boolean ativo
) {
    public AlunoResponseDTO(Aluno aluno) {
        this(aluno.getId(), aluno.getMatricula(), aluno.getNomeCompleto(), aluno.getDataCadastro(), aluno.getAtivo());
    }
}
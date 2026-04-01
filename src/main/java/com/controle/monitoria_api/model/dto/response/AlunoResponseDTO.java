package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Aluno;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AlunoResponseDTO(
        Long id,
        String matricula,
        String nomeCompleto,
        Long disciplinaId,
        String disciplinaSigla,
        String disciplinaDescricao,
        Long professorId,
        String professorNome,
        String semestre,
        String tipoMonitoria,
        String local,
        LocalDate dataInicio,
        LocalDate dataFim,
        LocalDateTime dataCadastro,
        Boolean ativo
) {
    public AlunoResponseDTO(Aluno aluno) {
        this(aluno.getId(), aluno.getMatricula(), aluno.getNomeCompleto(), aluno.getDisciplina().getId(), aluno.getDisciplina().getSigla(), aluno.getDisciplina().getDescricao(), aluno.getProfessor().getId(), aluno.getProfessor().getNomeCompleto(), aluno.getSemestre(), aluno.getTipoMonitoria(), aluno.getLocal(), aluno.getDataInicio(), aluno.getDataFim(), aluno.getDataCadastro(), aluno.getAtivo());
    }
}
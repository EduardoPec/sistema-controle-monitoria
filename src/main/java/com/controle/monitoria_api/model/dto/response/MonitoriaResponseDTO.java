package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Monitoria;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MonitoriaResponseDTO(
        Long id,
        Long alunoId,
        String alunoNome,
        String alunoMatricula,
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
        Integer numeroAlunosAtendidos,
        String ocorrencias,
        String parecerFinal,
        String status,
        LocalDateTime dataCadastro
) {
    public MonitoriaResponseDTO(Monitoria monitoria) {
        this(monitoria.getId(), monitoria.getAluno().getId(), monitoria.getAluno().getNomeCompleto(), monitoria.getAluno().getMatricula(), monitoria.getDisciplina().getId(), monitoria.getDisciplina().getSigla(), monitoria.getDisciplina().getDescricao(), monitoria.getProfessor().getId(), monitoria.getProfessor().getNomeCompleto(), monitoria.getSemestre(), monitoria.getTipoMonitoria(), monitoria.getLocal(), monitoria.getDataInicio(), monitoria.getDataFim(), monitoria.getNumeroAlunosAtendidos(), monitoria.getOcorrencias(), monitoria.getParecerFinal(), monitoria.getStatus(), monitoria.getDataCadastro());
    }
}
package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.RelatorioMonitoria;

import java.time.LocalDateTime;

public record RelatorioMonitoriaResponseDTO(
        Long id,
        Long monitoriaId,
        String alunoNome,
        String alunoMatricula,
        String disciplinaSigla,
        String professorNome,
        String semestre,
        Integer numeroAlunosAtendidos,
        String ocorrencias,
        String parecerFinal,
        LocalDateTime dataCadastro
) {
    public RelatorioMonitoriaResponseDTO(RelatorioMonitoria relatorio) {
        this(relatorio.getId(), relatorio.getMonitoria().getId(), relatorio.getMonitoria().getAluno().getNomeCompleto(), relatorio.getMonitoria().getAluno().getMatricula(), relatorio.getMonitoria().getDisciplina().getSigla(), relatorio.getMonitoria().getProfessor().getNomeCompleto(), relatorio.getMonitoria().getSemestre(), relatorio.getNumeroAlunosAtendidos(), relatorio.getOcorrencias(), relatorio.getParecerFinal(), relatorio.getDataCadastro());
    }
}
package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Monitoria;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MonitoriaResponseDTO(
        Long id,
        AlunoResponseDTO aluno,
        DisciplinaResponseDTO disciplina,
        ProfessorResponseDTO professor,
        String semestre,
        String tipoMonitoria,
        String local,
        LocalDate dataInicio,
        LocalDate dataFim,
        LocalDateTime dataCadastro,
        String status) {

    public MonitoriaResponseDTO(Monitoria monitoria) {
        this(
                monitoria.getId(),
                new AlunoResponseDTO(monitoria.getAluno()),
                new DisciplinaResponseDTO(monitoria.getDisciplina()),
                        new ProfessorResponseDTO(monitoria.getProfessor()),
                                monitoria.getSemestre(),
                                monitoria.getTipoMonitoria(),
                                monitoria.getLocal(),
                                monitoria.getDataInicio(),
                                monitoria.getDataFim(),
                                monitoria.getDataCadastro(),
                                monitoria.getStatus()
        );
    }
}
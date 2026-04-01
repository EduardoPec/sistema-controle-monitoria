package com.controle.monitoria_api.model.dto.request.monitoria;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MonitoriaAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        Long alunoId,
        Long disciplinaId,
        Long professorId,
        @Size(max = 20, message = "Semestre deve ter no máximo 20 caracteres!")
        String semestre,
        @Pattern(regexp = "PRESENCIAL|REMOTO", message = "Tipo deve ser PRESENCIAL ou REMOTO!")
        String tipoMonitoria,
        @Size(max = 200, message = "Local deve ter no máximo 200 caracteres!")
        String local,
        LocalDate dataInicio,
        LocalDate dataFim) {
}
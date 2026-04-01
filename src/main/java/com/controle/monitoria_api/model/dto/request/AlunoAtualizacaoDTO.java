package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlunoAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        @Size(max = 20, message = "Matrícula deve ter no máximo 20 caracteres!")
        String matricula,
        @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres!")
        String nomeCompleto,
        Long disciplinaId,
        Long professorId,
        @Size(max = 20, message = "Semestre deve ter no máximo 20 caracteres!")
        String semestre,
        @Pattern(regexp = "PRESENCIAL|REMOTO", message = "Tipo de monitoria deve ser PRESENCIAL ou REMOTO!")
        String tipoMonitoria,
        @Size(max = 200, message = "Local deve ter no máximo 200 caracteres!")
        String local,
        LocalDate dataInicio,
        LocalDate dataFim
) {}
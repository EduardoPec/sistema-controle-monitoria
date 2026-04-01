package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MonitoriaCriacaoDTO(
        @NotNull(message = "Aluno é obrigatório!")
        Long alunoId,
        @NotNull(message = "Disciplina é obrigatória!")
        Long disciplinaId,
        @NotNull(message = "Professor orientador é obrigatório!")
        Long professorId,
        @NotBlank(message = "Semestre é obrigatório!")
        @Size(max = 20, message = "Semestre deve ter no máximo 20 caracteres!")
        String semestre,
        @NotBlank(message = "Tipo de monitoria é obrigatório!")
        @Pattern(regexp = "PRESENCIAL|REMOTO", message = "Monitoria deve ser PRESENCIAL ou REMOTO!")
        String tipoMonitoria,
        @NotBlank(message = "Local é obrigatório!")
        @Size(max = 200, message = "Local deve ter no máximo 200 caracteres!")
        String local,
        @NotNull(message = "Data de início é obrigatória!")
        LocalDate dataInicio,
        @NotNull(message = "Data de fim é obrigatória!")
        LocalDate dataFim
) {}
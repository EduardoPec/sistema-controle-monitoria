package com.controle.monitoria_api.model.dto.request.aluno;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlunoAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        @Size(max = 20, message = "Matrícula deve ter no máximo 20 caracteres!")
        String matricula,
        @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres!")
        String nomeCompleto) {
}
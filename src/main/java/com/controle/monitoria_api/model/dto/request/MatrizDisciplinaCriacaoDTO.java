package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MatrizDisciplinaCriacaoDTO(
        @NotNull(message = "Matriz é obrigatória!")
        Long matrizId,
        @NotNull(message = "Disciplina é obrigatória!")
        Long disciplinaId,
        List<Long> preRequisitosIds) {
}

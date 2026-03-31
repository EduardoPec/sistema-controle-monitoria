package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MatrizDisciplinaCriacaoDTO(
        @NotNull(message = "O id da disciplina é obrigatório!")
        @Positive(message = "O id da disciplina deve ser positivo")
        Long disciplinaId,
        @Positive(message = "O id do pré-requisito deve ser positivo")
        Long preRequisitoId

) {}
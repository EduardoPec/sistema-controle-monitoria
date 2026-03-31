package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MatrizDisciplinaAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        Long matrizId,
        Long disciplinaId,
        List<Long> preRequisitosIds) {
}

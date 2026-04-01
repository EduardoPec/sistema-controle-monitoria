package com.controle.monitoria_api.model.dto.request.escola;

import jakarta.validation.constraints.NotNull;

public record EscolaAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        String nome,
        String coordenador,
        Long iesId) {
}

package com.controle.monitoria_api.model.dto.request.ies;

import jakarta.validation.constraints.NotNull;

public record IESAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        String nome,
        String endereco,
        String telefone) {
}

package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record IESAtualizacaoDTO(
        @NotNull
        Long id,
        String nome,
        String endereco,
        String telefone) {
}

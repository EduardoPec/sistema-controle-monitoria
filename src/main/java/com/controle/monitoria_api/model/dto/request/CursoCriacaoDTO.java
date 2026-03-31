package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoCriacaoDTO(
        @NotBlank(message = "Sigla é obrigatória!")
        @Size(max = 20, message = "Sigla deve ter no máximo 20 caracteres")
        String sigla,
        @NotBlank(message = "Descrição é obrigatória!")
        @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
        String descricao,
        @NotNull(message = "Escola é obrigatória!")
        Long escolaId,
        @NotBlank(message = "Turno é obrigatório!")
        @Size(max = 20, message = "Turno deve ter no máximo 20 caracteres")
        String turno,
        @NotBlank(message = "Coordenador é obrigatório!")
        @Size(max = 100, message = "Coordenador deve ter no máximo 100 caracteres")
        String coordenador) {
}

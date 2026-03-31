package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ProfessorAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        String matricula,
        String nomeCompleto,
        @Email(message = "Email inválido!")
        String email,
        String telefone,
        Long escolaId) {
}

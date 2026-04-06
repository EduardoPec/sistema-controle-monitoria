package com.controle.monitoria_api.model.dto.request.usuario;

import com.controle.monitoria_api.model.enums.PerfilUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCriacaoDTO(
    @NotBlank(message = "Login é obrigatório!")
    String login,
    @NotBlank(message = "Senha é obrigatória!")
    String senha,
    @NotNull(message = "Perfil é obrigatório!")
    PerfilUsuario perfil,
    Long professorId) {
}

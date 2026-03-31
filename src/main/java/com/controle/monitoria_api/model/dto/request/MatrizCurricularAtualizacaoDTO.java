package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MatrizCurricularAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres!")
        String nome,
        @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres!")
        String descricao,
        Long cursoId) {
}
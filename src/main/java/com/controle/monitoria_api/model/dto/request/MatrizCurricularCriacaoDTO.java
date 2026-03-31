package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MatrizCurricularCriacaoDTO(
        @NotBlank(message = "O nome da matriz é obrigatório!")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres!")
        String nome,
        @NotBlank(message = "A descrição da matriz é obrigatória!")
        @Size(max = 300, message = "A descrição deve ter no máximo 300 caracteres!")
        String descricao,
        @NotNull(message = "O curso é obrigatório!")
        @Positive(message = "O id do curso deve ser positivo")
        Long cursoId,
        @Valid
        List<MatrizDisciplinaCriacaoDTO> disciplinas

) {}
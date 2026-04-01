package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.*;

public record FormacaoAtualizacaoDTO(

        @NotNull(message = "O id é obrigatório!")
        Long id,

        @Size(max = 50, message = "A titulação deve ter no máximo 50 caracteres!")
        String titulacao,

        @Size(max = 150, message = "A instituição deve ter no máximo 150 caracteres!")
        String instituicao,

        @Size(max = 150, message = "O nome do curso deve ter no máximo 150 caracteres!")
        String nomeCurso,

        @Min(value = 1900, message = "Ano de conclusão inválido!")
        @Max(value = 2100, message = "Ano de conclusão inválido!")
        Integer anoConclusao
) {}
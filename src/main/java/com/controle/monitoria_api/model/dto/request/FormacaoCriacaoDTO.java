package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.*;

public record FormacaoCriacaoDTO(

        @NotNull(message = "O professor é obrigatório!")
        Long professorId,

        @NotBlank(message = "A titulação é obrigatória!")
        @Size(max = 50, message = "A titulação deve ter no máximo 50 caracteres!")
        String titulacao,

        @NotBlank(message = "A instituição é obrigatória!")
        @Size(max = 150, message = "A instituição deve ter no máximo 150 caracteres!")
        String instituicao,

        @NotBlank(message = "O nome do curso é obrigatório!")
        @Size(max = 150, message = "O nome do curso deve ter no máximo 150 caracteres!")
        String nomeCurso,

        @NotNull(message = "O ano de conclusão é obrigatório!")
        @Min(value = 1900, message = "Ano de conclusão inválido!")
        @Max(value = 2100, message = "Ano de conclusão inválido!")
        Integer anoConclusao
) {}
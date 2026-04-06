package com.controle.monitoria_api.model.dto.request.curso;

import com.controle.monitoria_api.model.enums.TurnoCurso;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoAtualizacaoDTO(
        @NotNull(message = "ID é obrigatório!")
        Long id,
        @Size(max = 20, message = "Sigla deve ter no máximo 20 caracteres")
        String sigla,
        @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
        String descricao,
        Long escolaId,
        TurnoCurso turno,
        @Size(max = 100, message = "Coordenador deve ter no máximo 100 caracteres")
        String coordenador) {
}

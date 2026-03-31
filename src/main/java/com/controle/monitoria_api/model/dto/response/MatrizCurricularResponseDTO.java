package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.MatrizCurricular;

import java.time.LocalDateTime;
import java.util.List;

public record MatrizCurricularResponseDTO(
        Long id,
        String nome,
        String descricao,
        CursoResponseDTO curso,
        LocalDateTime dataCadastro,
        Boolean ativo
) {

    public MatrizCurricularResponseDTO(MatrizCurricular matriz) { 
        this(matriz.getId(), matriz.getNome(), matriz.getDescricao(), new CursoResponseDTO(matriz.getCurso()), matriz.getDataCadastro(), matriz.getAtivo());
    }
}
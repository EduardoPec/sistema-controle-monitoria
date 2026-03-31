package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.MatrizCurricular;

import java.time.LocalDateTime;
import java.util.List;

public record MatrizCurricularResponseDTO(
        Long id,
        String nome,
        String descricao,
        Long cursoId,
        String cursoDescricao,
        List<MatrizDisciplinaResponseDTO> disciplinas,
        LocalDateTime dataCadastro,
        Boolean ativo
) {

    public MatrizCurricularResponseDTO(MatrizCurricular matriz) { 
        this(matriz.getId(), matriz.getNome(), matriz.getDescricao(), matriz.getCurso().getId(), matriz.getCurso().getDescricao(), matriz.getMatrizDisciplinas().stream().map(MatrizDisciplinaResponseDTO::new).toList(), matriz.getDataCadastro(), matriz.getAtivo()); 
    }
}
package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.MatrizDisciplina;

public record MatrizDisciplinaResponseDTO(
        Long id,
        Long disciplinaId,
        String disciplinaSigla,
        String disciplinaDescricao,
        Long preRequisitoId,
        String preRequisitoSigla
) {

    public MatrizDisciplinaResponseDTO(MatrizDisciplina md) { 
        this(md.getId(), md.getDisciplina().getId(), md.getDisciplina().getSigla(), md.getDisciplina().getDescricao(), md.getPreRequisito() != null ? md.getPreRequisito().getId() : null, md.getPreRequisito() != null ? md.getPreRequisito().getSigla() : null); 
    }
}
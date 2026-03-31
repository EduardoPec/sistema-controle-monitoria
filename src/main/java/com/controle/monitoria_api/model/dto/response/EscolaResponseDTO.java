package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.Escola;

import java.time.LocalDateTime;

public record EscolaResponseDTO(
        Long id,
        String nome,
        String coordenador,
        IESResponseDTO ies,
        LocalDateTime dataCadastro,
        Boolean ativo) {

    public EscolaResponseDTO(Escola escola) {
        this(escola.getId(), escola.getNome(), escola.getCoordenador(), new IESResponseDTO(escola.getIes()), escola.getDataCadastro(), escola.getAtivo());
    }
}

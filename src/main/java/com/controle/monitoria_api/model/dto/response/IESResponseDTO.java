package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.IES;

import java.time.LocalDateTime;

public record IESResponseDTO(Long id, String nome, String endereco, String telefone, LocalDateTime dataCadastro) {

    public IESResponseDTO(IES ies) {
        this(ies.getId(), ies.getNome(), ies.getEndereco(), ies.getTelefone(), ies.getDataCadastro());
    }
}

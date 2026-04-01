package com.controle.monitoria_api.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RelatorioMonitoriaCriacaoDTO(
        @NotNull(message = "ID da monitoria é obrigatório!")
        Long monitoriaId,
        @NotNull(message = "Número de alunos atendidos é obrigatório!")
        @Min(value = 0, message = "Número de alunos atendidos não pode ser negativo!")
        Integer numeroAlunosAtendidos,
        @Size(max = 500, message = "Ocorrências devem ter no máximo 500 caracteres!")
        String ocorrencias,
        @NotNull(message = "Parecer final é obrigatório!")
        @Size(max = 500, message = "Parecer final deve ter no máximo 500 caracteres!")
        String parecerFinal
) {}
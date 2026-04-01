package com.controle.monitoria_api.model.dto.response;

import com.controle.monitoria_api.model.RelatorioMonitoria;

public record RelatorioMonitoriaResponseDTO(
        Long id,
        MonitoriaResponseDTO monitoria,
        Integer numeroAlunosAtendidos,
        String ocorrencias,
        String parecerFinal)
{

    public RelatorioMonitoriaResponseDTO(RelatorioMonitoria relatorio) {
        this(relatorio.getId(), new MonitoriaResponseDTO(relatorio.getMonitoria()), relatorio.getNumeroAlunosAtendidos(), relatorio.getOcorrencias(), relatorio.getParecerFinal());
    }
}
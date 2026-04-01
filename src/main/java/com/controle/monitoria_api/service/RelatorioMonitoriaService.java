package com.controle.monitoria_api.service;

import com.controle.monitoria_api.exceptions.ValidacaoException;
import com.controle.monitoria_api.model.RelatorioMonitoria;
import com.controle.monitoria_api.model.dto.request.RelatorioMonitoriaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.RelatorioMonitoriaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.RelatorioMonitoriaResponseDTO;
import com.controle.monitoria_api.repository.MonitoriaRepository;
import com.controle.monitoria_api.repository.ProfessorRepository;
import com.controle.monitoria_api.repository.RelatorioMonitoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RelatorioMonitoriaService {

    private final RelatorioMonitoriaRepository relatorioRepository;
    private final MonitoriaRepository monitoriaRepository;
    private final ProfessorRepository professorRepository;

    @Transactional
    public RelatorioMonitoriaResponseDTO criar(RelatorioMonitoriaCriacaoDTO dto) {
        var monitoria = monitoriaRepository.findById(dto.monitoriaId())
                .orElseThrow(() -> new ValidacaoException("Monitoria não encontrada!"));

        if (!monitoria.getStatus().equals("FINALIZADA")) {
            throw new ValidacaoException("Só é possível criar um relatório para uma monitoria finalizada!");
        }

        if (relatorioRepository.existsByMonitoriaId(dto.monitoriaId())) {
            throw new ValidacaoException("Já existe um relatório para esta monitoria!");
        }

        var relatorio = new RelatorioMonitoria(dto, monitoria);
        var salvo = relatorioRepository.save(relatorio);
        return new RelatorioMonitoriaResponseDTO(salvo);
    }

    public Page<RelatorioMonitoriaResponseDTO> listarTodos(Pageable paginacao) {
        return relatorioRepository.findAll(paginacao)
                .map(RelatorioMonitoriaResponseDTO::new);
    }

    public Page<RelatorioMonitoriaResponseDTO> listarPorProfessor(Long professorId, Pageable paginacao) {
        if (!professorRepository.existsById(professorId)) {
            throw new ValidacaoException("Professor não encontrado!");
        }
        return relatorioRepository.findByMonitoriaProfessorId(professorId, paginacao)
                .map(RelatorioMonitoriaResponseDTO::new);
    }

    public RelatorioMonitoriaResponseDTO listarPorId(Long id) {
        var relatorio = relatorioRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Relatório não encontrado!"));
        return new RelatorioMonitoriaResponseDTO(relatorio);
    }

    public RelatorioMonitoriaResponseDTO listarPorMonitoria(Long monitoriaId) {
        if (!monitoriaRepository.existsById(monitoriaId)) {
            throw new ValidacaoException("Monitoria não encontrada!");
        }
        var relatorio = relatorioRepository.findByMonitoriaId(monitoriaId)
                .orElseThrow(() -> new ValidacaoException("Relatório não encontrado para esta monitoria!"));
        return new RelatorioMonitoriaResponseDTO(relatorio);
    }

    @Transactional
    public RelatorioMonitoriaResponseDTO atualizar(RelatorioMonitoriaAtualizacaoDTO dto) {
        var relatorio = relatorioRepository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Relatório não encontrado!"));

        relatorio.atualizarInformacoes(dto);
        return new RelatorioMonitoriaResponseDTO(relatorio);
    }

    @Transactional
    public void excluir(Long id) {
        if (!relatorioRepository.existsById(id)) {
            throw new ValidacaoException("Relatório não encontrado!");
        }
        relatorioRepository.deleteById(id);
    }
}
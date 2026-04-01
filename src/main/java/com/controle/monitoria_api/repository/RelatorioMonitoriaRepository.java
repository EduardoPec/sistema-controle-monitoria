package com.controle.monitoria_api.repository;

import com.controle.monitoria_api.model.RelatorioMonitoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelatorioMonitoriaRepository extends JpaRepository<RelatorioMonitoria, Long> {

    boolean existsByMonitoriaId(Long monitoriaId);

    Optional<RelatorioMonitoria> findByMonitoriaId(Long monitoriaId);

    Page<RelatorioMonitoria> findByMonitoriaProfessorId(Long professorId, Pageable paginacao);
}
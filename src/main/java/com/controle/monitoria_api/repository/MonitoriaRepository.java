package com.controle.monitoria_api.repository;

import com.controle.monitoria_api.model.Monitoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoriaRepository extends JpaRepository<Monitoria, Long> {

    Page<Monitoria> findByStatus(String status, Pageable paginacao);
    Page<Monitoria> findByProfessorId(Long professorId, Pageable paginacao);
    Page<Monitoria> findByAlunoId(Long alunoId, Pageable paginacao);

    boolean existsByAlunoIdAndDisciplinaIdAndSemestreAndStatusNot(Long alunoId, Long disciplinaId, String semestre, String status);
}
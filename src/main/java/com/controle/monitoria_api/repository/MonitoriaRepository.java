package com.controle.monitoria_api.repository;

import com.controle.monitoria_api.model.Monitoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoriaRepository extends JpaRepository<Monitoria, Long> {

    Page<Monitoria> findByStatus(String status, Pageable paginacao);
    Page<Monitoria> findByProfessorId(Long professorId, Pageable paginacao);
    Page<Monitoria> findByProfessorIdAndStatus(Long professorId, String status, Pageable paginacao);
    Page<Monitoria> findByDisciplinaId(Long disciplinaId, Pageable paginacao);
    Page<Monitoria> findByAlunoId(Long alunoId, Pageable paginacao);

    boolean existsByAlunoIdAndDisciplinaIdAndSemestre(Long alunoId, Long disciplinaId, String semestre);
    boolean existsByAlunoIdAndDisciplinaIdAndSemestreAndIdNot(Long alunoId, Long disciplinaId, String semestre, Long id);

    long countByDisciplinaIdAndSemestre(Long disciplinaId, String semestre);
}
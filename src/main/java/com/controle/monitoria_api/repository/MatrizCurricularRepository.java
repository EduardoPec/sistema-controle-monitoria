package com.controle.monitoria_api.repository;

import com.controle.monitoria_api.model.MatrizCurricular;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatrizCurricularRepository extends JpaRepository<MatrizCurricular, Long> {

    Page<MatrizCurricular> findAllByAtivoTrue(Pageable paginacao);
    Page<MatrizCurricular> findAllByAtivoFalse(Pageable paginacao);
    Page<MatrizCurricular> findByCursoId(Long cursoId, Pageable paginacao);

    boolean existsByCursoIdAndAtivoTrue(Long cursoId);
    boolean existsByCursoIdAndAtivoTrueAndIdNot(Long cursoId, Long id);
}
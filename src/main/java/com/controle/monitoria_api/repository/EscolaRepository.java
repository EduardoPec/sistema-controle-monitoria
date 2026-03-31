package com.controle.monitoria_api.repository;

import com.controle.monitoria_api.model.Escola;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscolaRepository extends JpaRepository<Escola, Long> {

    boolean existsByNomeAndIesId(String nome, Long iesId);

    Page<Escola> findAllByAtivoTrue(Pageable paginacao);

    Page<Escola> findAllByAtivoFalse(Pageable paginacao);

    Page<Escola> findByIesId(Long iesId, Pageable paginacao);

    boolean existsByNomeAndIesIdAndIdNot(String nomeFinal, Long iesIdFinal, Long id);
}

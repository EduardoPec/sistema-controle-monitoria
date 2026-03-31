package com.controle.monitoria_api.service;

import com.controle.monitoria_api.exceptions.ValidacaoException;
import com.controle.monitoria_api.model.Curso;
import com.controle.monitoria_api.model.Disciplina;
import com.controle.monitoria_api.model.MatrizCurricular;
import com.controle.monitoria_api.model.MatrizDisciplina;
import com.controle.monitoria_api.model.dto.request.MatrizCurricularAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.MatrizCurricularCriacaoDTO;
import com.controle.monitoria_api.model.dto.request.MatrizDisciplinaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.MatrizCurricularResponseDTO;
import com.controle.monitoria_api.repository.CursoRepository;
import com.controle.monitoria_api.repository.DisciplinaRepository;
import com.controle.monitoria_api.repository.MatrizCurricularRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatrizCurricularService {

    private final MatrizCurricularRepository matrizRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;

    @Transactional
    public MatrizCurricularResponseDTO criar(MatrizCurricularCriacaoDTO dto) {
        var curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new ValidacaoException("Curso não encontrado!"));

        if (matrizRepository.existsByCursoIdAndAtivoTrue(dto.cursoId())) {
            throw new ValidacaoException("Já existe uma matriz ativa para este curso! Inative a atual antes de criar uma nova.");
        }

        var matriz = new MatrizCurricular(dto, curso);

        if (dto.disciplinas() != null && !dto.disciplinas().isEmpty()) {
            List<MatrizDisciplina> vinculos = montarVinculos(dto.disciplinas(), matriz);
            matriz.getMatrizDisciplinas().addAll(vinculos);
        }

        var salvo = matrizRepository.save(matriz);
        return new MatrizCurricularResponseDTO(salvo);
    }

    public Page<MatrizCurricularResponseDTO> listarTodos(Pageable paginacao) {
        return matrizRepository.findAll(paginacao)
                .map(MatrizCurricularResponseDTO::new);
    }

    public Page<MatrizCurricularResponseDTO> listarAtivos(Pageable paginacao) {
        return matrizRepository.findAllByAtivoTrue(paginacao)
                .map(MatrizCurricularResponseDTO::new);
    }

    public Page<MatrizCurricularResponseDTO> listarInativos(Pageable paginacao) {
        return matrizRepository.findAllByAtivoFalse(paginacao)
                .map(MatrizCurricularResponseDTO::new);
    }

    public Page<MatrizCurricularResponseDTO> listarPorCurso(Long cursoId, Pageable paginacao) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new ValidacaoException("Curso não encontrado!");
        }
        return matrizRepository.findByCursoId(cursoId, paginacao)
                .map(MatrizCurricularResponseDTO::new);
    }

    public MatrizCurricularResponseDTO listarPorId(Long id) {
        var matriz = matrizRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Matriz curricular não encontrada!"));
        return new MatrizCurricularResponseDTO(matriz);
    }

    @Transactional
    public MatrizCurricularResponseDTO atualizar(MatrizCurricularAtualizacaoDTO dto) {
        var matriz = matrizRepository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Matriz curricular não encontrada!"));

        Curso novoCurso = null;
        if (dto.cursoId() != null) {
            novoCurso = cursoRepository.findById(dto.cursoId())
                    .orElseThrow(() -> new ValidacaoException("Curso não encontrado!"));

            if (!dto.cursoId().equals(matriz.getCurso().getId())) {
                if (matrizRepository.existsByCursoIdAndAtivoTrue(dto.cursoId())) {
                    throw new ValidacaoException("Já existe uma matriz ativa para o curso informado!");
                }
            }
        }

        matriz.atualizarInformacoes(dto, novoCurso);

        if (dto.disciplinas() != null) {
            matriz.getMatrizDisciplinas().clear();
            List<MatrizDisciplina> novosVinculos = montarVinculos(dto.disciplinas(), matriz);
            matriz.getMatrizDisciplinas().addAll(novosVinculos);
        }

        return new MatrizCurricularResponseDTO(matriz);
    }

    @Transactional
    public void inativar(Long id) {
        var matriz = matrizRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Matriz curricular não encontrada!"));
        matriz.inativar();
        matrizRepository.save(matriz);
    }

    @Transactional
    public void ativar(Long id) {
        var matriz = matrizRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Matriz curricular não encontrada!"));

        if (matrizRepository.existsByCursoIdAndAtivoTrueAndIdNot(matriz.getCurso().getId(), id)) {
            throw new ValidacaoException("Já existe uma matriz ativa para este curso! Inative a atual antes de ativar esta.");
        }

        matriz.ativar();
        matrizRepository.save(matriz);
    }

    private List<MatrizDisciplina> montarVinculos(List<MatrizDisciplinaCriacaoDTO> dtos, MatrizCurricular matriz) {
        List<MatrizDisciplina> vinculos = new ArrayList<>();

        for (MatrizDisciplinaCriacaoDTO item : dtos) {
            var disciplina = disciplinaRepository.findById(item.disciplinaId())
                    .orElseThrow(() -> new ValidacaoException("Disciplina não encontrada: id " + item.disciplinaId()));

            Disciplina preRequisito = null;
            if (item.preRequisitoId() != null) {
                preRequisito = disciplinaRepository.findById(item.preRequisitoId())
                        .orElseThrow(() -> new ValidacaoException("Pré-requisito não encontrado: id " + item.preRequisitoId()));

                if (preRequisito.getId().equals(disciplina.getId())) {
                    throw new ValidacaoException("Uma disciplina não pode ser pré-requisito de si mesma!");
                }
            }

            vinculos.add(new MatrizDisciplina(matriz, disciplina, preRequisito));
        }

        return vinculos;
    }
}
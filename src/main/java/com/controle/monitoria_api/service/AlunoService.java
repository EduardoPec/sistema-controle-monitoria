package com.controle.monitoria_api.service;

import com.controle.monitoria_api.exceptions.ValidacaoException;
import com.controle.monitoria_api.model.Aluno;
import com.controle.monitoria_api.model.Disciplina;
import com.controle.monitoria_api.model.Professor;
import com.controle.monitoria_api.model.dto.request.AlunoAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.AlunoCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.AlunoResponseDTO;
import com.controle.monitoria_api.repository.AlunoRepository;
import com.controle.monitoria_api.repository.DisciplinaRepository;
import com.controle.monitoria_api.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    @Transactional
    public AlunoResponseDTO criar(AlunoCriacaoDTO dto) {
        var disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new ValidacaoException("Disciplina não encontrada!"));

        var professor = professorRepository.findById(dto.professorId())
                .orElseThrow(() -> new ValidacaoException("Professor não encontrado!"));

        if (alunoRepository.existsByMatricula(dto.matricula())) {
            throw new ValidacaoException("Já existe um aluno com esta matrícula!");
        }

        if (alunoRepository.existsByMatriculaAndSemestre(dto.matricula(), dto.semestre())) {
            throw new ValidacaoException("Este aluno já está atuando como monitor em uma disciplina neste semestre!");
        }

        if (!dto.dataFim().isAfter(dto.dataInicio())) {
            throw new ValidacaoException("A data de fim deve ser posterior à data de início!");
        }

        var aluno = new Aluno(dto, disciplina, professor);
        var salvo = alunoRepository.save(aluno);
        return new AlunoResponseDTO(salvo);
    }

    public Page<AlunoResponseDTO> listarTodos(Pageable paginacao) {
        return alunoRepository.findAll(paginacao)
                .map(AlunoResponseDTO::new);
    }

    public Page<AlunoResponseDTO> listarAtivos(Pageable paginacao) {
        return alunoRepository.findAllByAtivoTrue(paginacao)
                .map(AlunoResponseDTO::new);
    }

    public Page<AlunoResponseDTO> listarInativos(Pageable paginacao) {
        return alunoRepository.findAllByAtivoFalse(paginacao)
                .map(AlunoResponseDTO::new);
    }

    public Page<AlunoResponseDTO> listarPorProfessor(Long professorId, Pageable paginacao) {
        if (!professorRepository.existsById(professorId)) {
            throw new ValidacaoException("Professor não encontrado!");
        }
        return alunoRepository.findByProfessorId(professorId, paginacao)
                .map(AlunoResponseDTO::new);
    }

    public Page<AlunoResponseDTO> listarPorDisciplina(Long disciplinaId, Pageable paginacao) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new ValidacaoException("Disciplina não encontrada!");
        }
        return alunoRepository.findByDisciplinaId(disciplinaId, paginacao)
                .map(AlunoResponseDTO::new);
    }

    public AlunoResponseDTO listarPorId(Long id) {
        var aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Aluno não encontrado!"));
        return new AlunoResponseDTO(aluno);
    }

    @Transactional
    public AlunoResponseDTO atualizar(AlunoAtualizacaoDTO dto) {
        var aluno = alunoRepository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Aluno não encontrado!"));

        Disciplina novaDisciplina = null;
        if (dto.disciplinaId() != null) {
            novaDisciplina = disciplinaRepository.findById(dto.disciplinaId())
                    .orElseThrow(() -> new ValidacaoException("Disciplina não encontrada!"));
        }

        Professor novoProfessor = null;
        if (dto.professorId() != null) {
            novoProfessor = professorRepository.findById(dto.professorId())
                    .orElseThrow(() -> new ValidacaoException("Professor não encontrado!"));
        }

        validarMatriculaUnicaNaAtualizacao(dto, aluno);
        validarMonitoriaUnicaPorSemestreNaAtualizacao(dto, aluno);
        validarDatasNaAtualizacao(dto, aluno);

        aluno.atualizarInformacoes(dto, novaDisciplina, novoProfessor);
        return new AlunoResponseDTO(aluno);
    }

    @Transactional
    public void inativar(Long id) {
        var aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Aluno não encontrado!"));
        aluno.inativar();
        alunoRepository.save(aluno);
    }

    @Transactional
    public void ativar(Long id) {
        var aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Aluno não encontrado!"));
        aluno.ativar();
        alunoRepository.save(aluno);
    }

    private void validarMatriculaUnicaNaAtualizacao(AlunoAtualizacaoDTO dto, Aluno aluno) {
        String matriculaFinal = dto.matricula() != null ? dto.matricula() : aluno.getMatricula();

        if (alunoRepository.existsByMatriculaAndIdNot(matriculaFinal, aluno.getId())) {
            throw new ValidacaoException("Já existe outro aluno com esta matrícula!");
        }
    }

    private void validarMonitoriaUnicaPorSemestreNaAtualizacao(AlunoAtualizacaoDTO dto, Aluno aluno) {
        String matriculaFinal = dto.matricula() != null ? dto.matricula() : aluno.getMatricula();
        String semestreFinal = dto.semestre() != null ? dto.semestre() : aluno.getSemestre();

        if (alunoRepository.existsByMatriculaAndSemestreAndIdNot(matriculaFinal, semestreFinal, aluno.getId())) {
            throw new ValidacaoException("Este aluno já está atuando como monitor em uma disciplina neste semestre!");
        }
    }

    private void validarDatasNaAtualizacao(AlunoAtualizacaoDTO dto, Aluno aluno) {
        var dataInicio = dto.dataInicio() != null ? dto.dataInicio() : aluno.getDataInicio();
        var dataFim = dto.dataFim() != null ? dto.dataFim() : aluno.getDataFim();

        if (!dataFim.isAfter(dataInicio)) {
            throw new ValidacaoException("A data de fim deve ser posterior à data de início!");
        }
    }
}
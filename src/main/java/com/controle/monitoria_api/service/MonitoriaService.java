package com.controle.monitoria_api.service;

import com.controle.monitoria_api.exceptions.ValidacaoException;
import com.controle.monitoria_api.model.Aluno;
import com.controle.monitoria_api.model.Disciplina;
import com.controle.monitoria_api.model.Monitoria;
import com.controle.monitoria_api.model.Professor;
import com.controle.monitoria_api.model.dto.request.monitoria.MonitoriaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.monitoria.MonitoriaCriacaoDTO;
import com.controle.monitoria_api.model.dto.response.MonitoriaResponseDTO;
import com.controle.monitoria_api.repository.AlunoRepository;
import com.controle.monitoria_api.repository.DisciplinaRepository;
import com.controle.monitoria_api.repository.MonitoriaRepository;
import com.controle.monitoria_api.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonitoriaService {

    private final MonitoriaRepository monitoriaRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    @Transactional
    public MonitoriaResponseDTO criar(MonitoriaCriacaoDTO dto) {
        var aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new ValidacaoException("Aluno não encontrado!"));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new ValidacaoException("Disciplina não encontrada!"));

        Professor professor = professorRepository.findById(dto.professorId())
                .orElseThrow(() -> new ValidacaoException("Professor não encontrado!"));

        if (monitoriaRepository.existsByAlunoIdAndDisciplinaIdAndSemestreAndStatusNot(dto.alunoId(), dto.disciplinaId(), dto.semestre(), "FINALIZADA")) {
            throw new ValidacaoException("Aluno já é monitor nesta disciplina neste semestre!");
        }

        var monitoria = new Monitoria(dto, aluno, disciplina, professor);
        var salvar = monitoriaRepository.save(monitoria);
        return new MonitoriaResponseDTO(salvar);
    }

    public Page<MonitoriaResponseDTO> listarTodos(Pageable paginacao) {
        return monitoriaRepository.findAll(paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorProfessor(Long professorId, Pageable paginacao) {
        if (!professorRepository.existsById(professorId)) {
            throw new ValidacaoException("Professor não encontrado!");
        }
        return monitoriaRepository.findByProfessorId(professorId, paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorAluno(Long alunoId, Pageable paginacao) {
        if (!alunoRepository.existsById(alunoId)) {
            throw new ValidacaoException("Aluno não encontrado!");
        }
        return monitoriaRepository.findByAlunoId(alunoId, paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorStatus(String status, Pageable paginacao) {
        return monitoriaRepository.findByStatus(status, paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public MonitoriaResponseDTO listarPorId(Long id) {
        var monitoria = monitoriaRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Monitoria não encontrada!"));
        return new MonitoriaResponseDTO(monitoria);
    }

    @Transactional
    public MonitoriaResponseDTO atualizar(MonitoriaAtualizacaoDTO dto) {
        var monitoria = monitoriaRepository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Monitoria não encontrada!"));

        Aluno novoAluno = null;
        if (dto.alunoId() != null) {
            novoAluno = alunoRepository.findById(dto.alunoId())
                    .orElseThrow(() -> new ValidacaoException("Aluno não encontrado!"));
        }

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

        monitoria.atualizarInformacoes(dto, novoAluno, novaDisciplina, novoProfessor);
        return new MonitoriaResponseDTO(monitoria);
    }

    @Transactional
    public void finalizar(Long id) {
        var monitoria = monitoriaRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Monitoria não encontrada!"));
        monitoria.finalizar();
        monitoriaRepository.save(monitoria);
    }
}
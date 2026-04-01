package com.controle.monitoria_api.service;

import com.controle.monitoria_api.exceptions.ValidacaoException;
import com.controle.monitoria_api.model.Aluno;
import com.controle.monitoria_api.model.Disciplina;
import com.controle.monitoria_api.model.Monitoria;
import com.controle.monitoria_api.model.Professor;
import com.controle.monitoria_api.model.dto.request.MonitoriaAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.MonitoriaCriacaoDTO;
import com.controle.monitoria_api.model.dto.request.MonitoriaFinalizacaoDTO;
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

        var disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new ValidacaoException("Disciplina não encontrada!"));

        var professor = professorRepository.findById(dto.professorId())
                .orElseThrow(() -> new ValidacaoException("Professor não encontrado!"));

        if (monitoriaRepository.existsByAlunoIdAndDisciplinaIdAndSemestre(
                dto.alunoId(), dto.disciplinaId(), dto.semestre())) {
            throw new ValidacaoException("Este aluno já possui uma monitoria nesta disciplina neste semestre!");
        }

        if (!dto.dataFim().isAfter(dto.dataInicio())) {
            throw new ValidacaoException("A data de fim deve ser posterior à data de início!");
        }

        var monitoria = new Monitoria(dto, aluno, disciplina, professor);
        var salvo = monitoriaRepository.save(monitoria);
        return new MonitoriaResponseDTO(salvo);
    }

    public Page<MonitoriaResponseDTO> listarTodos(Pageable paginacao) {
        return monitoriaRepository.findAll(paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarEmAndamento(Pageable paginacao) {
        return monitoriaRepository.findByStatus("EM_ANDAMENTO", paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarFinalizadas(Pageable paginacao) {
        return monitoriaRepository.findByStatus("FINALIZADA", paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorProfessor(Long professorId, Pageable paginacao) {
        if (!professorRepository.existsById(professorId)) {
            throw new ValidacaoException("Professor não encontrado!");
        }
        return monitoriaRepository.findByProfessorId(professorId, paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorProfessorEmAndamento(Long professorId, Pageable paginacao) {
        if (!professorRepository.existsById(professorId)) {
            throw new ValidacaoException("Professor não encontrado!");
        }
        return monitoriaRepository.findByProfessorIdAndStatus(professorId, "EM_ANDAMENTO", paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorProfessorFinalizadas(Long professorId, Pageable paginacao) {
        if (!professorRepository.existsById(professorId)) {
            throw new ValidacaoException("Professor não encontrado!");
        }
        return monitoriaRepository.findByProfessorIdAndStatus(professorId, "FINALIZADA", paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorDisciplina(Long disciplinaId, Pageable paginacao) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new ValidacaoException("Disciplina não encontrada!");
        }
        return monitoriaRepository.findByDisciplinaId(disciplinaId, paginacao)
                .map(MonitoriaResponseDTO::new);
    }

    public Page<MonitoriaResponseDTO> listarPorAluno(Long alunoId, Pageable paginacao) {
        if (!alunoRepository.existsById(alunoId)) {
            throw new ValidacaoException("Aluno não encontrado!");
        }
        return monitoriaRepository.findByAlunoId(alunoId, paginacao)
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

        if (monitoria.getStatus().equals("FINALIZADA")) {
            throw new ValidacaoException("Não é possível alterar uma monitoria já finalizada!");
        }

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

        validarDuplicidadeNaAtualizacao(dto, monitoria);
        validarDatasNaAtualizacao(dto, monitoria);

        monitoria.atualizarInformacoes(dto, novoAluno, novaDisciplina, novoProfessor);
        return new MonitoriaResponseDTO(monitoria);
    }

    @Transactional
    public MonitoriaResponseDTO finalizar(MonitoriaFinalizacaoDTO dto) {
        var monitoria = monitoriaRepository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Monitoria não encontrada!"));

        if (monitoria.getStatus().equals("FINALIZADA")) {
            throw new ValidacaoException("Esta monitoria já foi finalizada!");
        }

        monitoria.finalizarMonitoria(dto.numeroAlunosAtendidos(), dto.ocorrencias(), dto.parecerFinal());
        monitoriaRepository.save(monitoria);
        return new MonitoriaResponseDTO(monitoria);
    }

    public long contarPorDisciplinaESemestre(Long disciplinaId, String semestre) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new ValidacaoException("Disciplina não encontrada!");
        }
        return monitoriaRepository.countByDisciplinaIdAndSemestre(disciplinaId, semestre);
    }

    private void validarDuplicidadeNaAtualizacao(MonitoriaAtualizacaoDTO dto, Monitoria monitoria) {
        Long alunoIdFinal = dto.alunoId() != null ? dto.alunoId() : monitoria.getAluno().getId();
        Long disciplinaIdFinal = dto.disciplinaId() != null ? dto.disciplinaId() : monitoria.getDisciplina().getId();
        String semestreFinal = dto.semestre() != null ? dto.semestre() : monitoria.getSemestre();

        if (monitoriaRepository.existsByAlunoIdAndDisciplinaIdAndSemestreAndIdNot(
                alunoIdFinal, disciplinaIdFinal, semestreFinal, monitoria.getId())) {
            throw new ValidacaoException("Este aluno já possui uma monitoria nesta disciplina neste semestre!");
        }
    }

    private void validarDatasNaAtualizacao(MonitoriaAtualizacaoDTO dto, Monitoria monitoria) {
        var dataInicio = dto.dataInicio() != null ? dto.dataInicio() : monitoria.getDataInicio();
        var dataFim = dto.dataFim() != null ? dto.dataFim() : monitoria.getDataFim();

        if (!dataFim.isAfter(dataInicio)) {
            throw new ValidacaoException("A data de fim deve ser posterior à data de início!");
        }
    }
}
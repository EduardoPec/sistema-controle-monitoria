package com.controle.monitoria_api.model;

import com.controle.monitoria_api.model.dto.request.AlunoAtualizacaoDTO;
import com.controle.monitoria_api.model.dto.request.AlunoCriacaoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "alunos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String matricula;

    @Column(name = "nome_completo", nullable = false, length = 100)
    private String nomeCompleto;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Column(nullable = false, length = 20)
    private String semestre;

    @Column(name = "tipo_monitoria", nullable = false, length = 20)
    private String tipoMonitoria;

    @Column(nullable = false, length = 200)
    private String local;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(nullable = false)
    private Boolean ativo;

    public Aluno(AlunoCriacaoDTO dto, Disciplina disciplina, Professor professor) {
        this.matricula = dto.matricula();
        this.nomeCompleto = dto.nomeCompleto();
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = dto.semestre();
        this.tipoMonitoria = dto.tipoMonitoria();
        this.local = dto.local();
        this.dataInicio = dto.dataInicio();
        this.dataFim = dto.dataFim();
        this.ativo = true;
    }

    @PrePersist
    public void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }

    public void atualizarInformacoes(AlunoAtualizacaoDTO dto, Disciplina disciplina, Professor professor) {
        if (dto.matricula() != null) {
            this.matricula = dto.matricula();
        }
        if (dto.nomeCompleto() != null) {
            this.nomeCompleto = dto.nomeCompleto();
        }
        if (dto.disciplinaId() != null && disciplina != null) {
            this.disciplina = disciplina;
        }
        if (dto.professorId() != null && professor != null) {
            this.professor = professor;
        }
        if (dto.semestre() != null) {
            this.semestre = dto.semestre();
        }
        if (dto.tipoMonitoria() != null) {
            this.tipoMonitoria = dto.tipoMonitoria();
        }
        if (dto.local() != null) {
            this.local = dto.local();
        }
        if (dto.dataInicio() != null) {
            this.dataInicio = dto.dataInicio();
        }
        if (dto.dataFim() != null) {
            this.dataFim = dto.dataFim();
        }
    }

    public void inativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
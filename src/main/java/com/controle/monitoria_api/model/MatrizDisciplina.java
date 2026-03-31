package com.controle.monitoria_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matrizes_disciplinas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"matriz_curricular_id", "disciplina_id"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class MatrizDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "matriz_curricular_id", nullable = false)
    private MatrizCurricular matrizCurricular;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "pre_requisito_id")
    private Disciplina preRequisito;

    public MatrizDisciplina(MatrizCurricular matrizCurricular, Disciplina disciplina, Disciplina preRequisito) {
        this.matrizCurricular = matrizCurricular;
        this.disciplina = disciplina;
        this.preRequisito = preRequisito;
    }
}
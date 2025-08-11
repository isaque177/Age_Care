// (Opcional) ProfissionalPorEspecialidadeModel.java
package com.example.AgeCare.model;

import com.example.AgeCare.id.ProfissionalEspecialidadeid;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@IdClass(ProfissionalEspecialidadeid.class)
@Table(name = "profissional_especializacoes")
public class ProfissionalPorEspecialidadeModel {

    @Id
    @ManyToOne @JoinColumn(name = "profissional_id")
    @JsonIgnore
    private ProfissionalModel profissional;

    @Id
    @ManyToOne @JoinColumn(name = "especializacao_id")
    @JsonIgnore
    private EspecialidadeModel especialidade;
}

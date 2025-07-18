package com.example.AgeCare.model;

import com.example.AgeCare.id.ProfissionalEspecialidadeid;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "profissional_especializacoes")
@IdClass(ProfissionalEspecialidadeid.class)
@Schema(description = "Relaciona profissionais às suas especialidades.")
public class ProfissionalPorEspecialidadeModel {
    @Id
    @ManyToOne
    private ProfissionalModel profissional;

    @Id
    @ManyToOne
    private EspecialidadeModel especialidade;
}

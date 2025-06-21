package com.example.AgeCare.model;

import java.sql.Date;
import com.example.AgeCare.model.EspecialidadeModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "EscpecialidadeProfissional")
@Getter
@Setter

public class ProfissionalPorEspecialidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalModel profissional;

    @ManyToOne
    @JoinColumn(name = "especialidade_id")
    private EspecialidadeModel especialidade;
    private String detalhes;
    private Date data_inicio;
    private Date data_fim;

}

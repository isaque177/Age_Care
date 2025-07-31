package com.example.AgeCare.id;


import java.io.Serializable;
import java.util.Objects;

/**
 * Classe de chave composta para ProfissionalPorEspecialidadeModel.
 */
public class ProfissionalEspecialidadeid implements Serializable {

    private Long profissional;
    private Long especialidade;

    public ProfissionalEspecialidadeid() {
    }

    public ProfissionalEspecialidadeid(Long profissional, Long especialidade) {
        this.profissional = profissional;
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfissionalEspecialidadeid)) return false;
        ProfissionalEspecialidadeid that = (ProfissionalEspecialidadeid) o;
        return Objects.equals(profissional, that.profissional) &&
               Objects.equals(especialidade, that.especialidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profissional, especialidade);
    }
}

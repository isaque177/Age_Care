package com.example.AgeCare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AgeCare.model.ServicoModel;


public interface ServicoRepository extends JpaRepository<ServicoModel, Long> {

@Query("SELECT s FROM ServicoModel s WHERE s.profissional.id = :profissionalId")
List<ServicoModel> findServicosByProfissionalId(@Param("profissionalId") Long profissionalId);
}

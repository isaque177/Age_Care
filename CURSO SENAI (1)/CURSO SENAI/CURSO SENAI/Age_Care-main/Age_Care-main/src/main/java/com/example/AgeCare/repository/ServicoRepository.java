package com.example.AgeCare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AgeCare.model.ServicoModel;


public interface ServicoRepository extends JpaRepository<ServicoModel, Long> {

@Query("SELECT p.servico FROM ProfissionalModel p WHERE p.id = :profissionalId")
ServicoModel findServicoByProfissionalId(@Param("profissionalId") Long profissionalId);


}

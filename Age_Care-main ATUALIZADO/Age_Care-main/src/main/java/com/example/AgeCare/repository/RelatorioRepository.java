package com.example.AgeCare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AgeCare.model.RelatorioModel;

@Repository
public interface RelatorioRepository extends JpaRepository<RelatorioModel, Long> {
}
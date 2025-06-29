package com.example.AgeCare.repository;

import com.example.AgeCare.model.RelatorioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends JpaRepository<RelatorioModel, Long> {

}

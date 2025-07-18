package com.example.AgeCare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AgeCare.model.ProfissionalModel;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalModel, Long> {

}

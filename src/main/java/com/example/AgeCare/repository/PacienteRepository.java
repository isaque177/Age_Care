package com.example.AgeCare.repository;

import com.example.AgeCare.model.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {

}

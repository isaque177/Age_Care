package com.example.AgeCare.repository;

import com.example.AgeCare.model.EspecialidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<EspecialidadeModel, Long> {

}
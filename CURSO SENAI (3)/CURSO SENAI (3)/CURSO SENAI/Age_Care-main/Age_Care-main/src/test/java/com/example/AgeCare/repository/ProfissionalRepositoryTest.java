// ProfissionalRepositoryTest.java
package com.example.AgeCare.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;      
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.AgeCare.model.ProfissionalModel;

@DataJpaTest      
class ProfissionalRepositoryTest {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Test
    void salvarProfissional_gravaERecupera() {
        ProfissionalModel novo = new ProfissionalModel(/* … */);
        ProfissionalModel salvo = profissionalRepository.save(novo);

        assertNotNull(salvo.getId());
        assertTrue(profissionalRepository.findById(salvo.getId()).isPresent());
    }
}

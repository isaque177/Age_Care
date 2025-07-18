package com.example.AgeCare.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.AgeCare.model.ProfissionalModel;
import com.example.AgeCare.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

     @Autowired
    private ProfissionalRepository repository;

    
    public List<ProfissionalModel> listar(){
        return repository.findAll();
    }

    public ProfissionalModel salvar(ProfissionalModel profissional){
        return repository.save(profissional);
    }

    public Optional<ProfissionalModel> buscarPorId(Long id){
        return repository.findById(id);
    }

    public ProfissionalModel atualizar(Long id, ProfissionalModel profissional) {
        profissional.setId(id);
        return repository.save(profissional);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
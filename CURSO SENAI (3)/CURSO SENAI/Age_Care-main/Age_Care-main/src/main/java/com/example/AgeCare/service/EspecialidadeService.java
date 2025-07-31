package com.example.AgeCare.service;


import com.example.AgeCare.model.EspecialidadeModel;
import com.example.AgeCare.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repository;

    public List<EspecialidadeModel> listar() {
        return repository.findAll();
    }

    public EspecialidadeModel salvar(EspecialidadeModel especialidade) {
        return repository.save(especialidade);
    }

    public Optional<EspecialidadeModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EspecialidadeModel atualizar(Long id, EspecialidadeModel especialidade) {
        especialidade.setId(id);
        return repository.save(especialidade);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }



}
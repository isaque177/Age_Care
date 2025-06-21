package com.example.AgeCare.service;

import com.example.AgeCare.model.EnderecoModel;
import com.example.AgeCare.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public List<EnderecoModel> listar() {
        return repository.findAll();
    }

    public Optional<EnderecoModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EnderecoModel salvar(EnderecoModel endereco) {
        return repository.save(endereco);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public EnderecoModel atualizar(Long id, EnderecoModel endereco) {
        endereco.setId(id);
        return repository.save(endereco);
    }
}
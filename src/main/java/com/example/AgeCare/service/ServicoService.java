package com.example.AgeCare.service;

import com.example.AgeCare.model.ServicoModel;
import com.example.AgeCare.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;


    public List<ServicoModel> listar(){
        return repository.findAll();

    }

    public Optional<ServicoModel> buscarPorId(Long id){
        return repository.findById(id);
    }

    public ServicoModel salvar(ServicoModel servico){
        return repository.save(servico);
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
    public ServicoModel atualizar(Long id, ServicoModel servico){
        servico.setId(servico.getId());
        return repository.save(servico);
    }

}

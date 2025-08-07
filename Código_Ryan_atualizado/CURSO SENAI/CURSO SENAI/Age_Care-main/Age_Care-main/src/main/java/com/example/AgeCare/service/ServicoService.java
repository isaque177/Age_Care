package com.example.AgeCare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AgeCare.model.ServicoModel;
import com.example.AgeCare.repository.ServicoRepository;

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

    public ServicoModel salvar(ServicoModel servico) {
    if (servico.getPreco() == null) {
        servico.setPreco(0.0);
    }
    
    return repository.save(servico);
}


    public List<ServicoModel> buscarPorProfissionalId(Long profissionalId) {
    return repository.findServicosByProfissionalId(profissionalId);
    }

    
    public void deletar(Long id){
        repository.deleteById(id);
    }
    public ServicoModel atualizar(Long id, ServicoModel servico){
        servico.setId(servico.getId());
        return repository.save(servico);
    }

}

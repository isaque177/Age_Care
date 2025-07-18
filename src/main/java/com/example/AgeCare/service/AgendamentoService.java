
package com.example.AgeCare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AgeCare.model.AgendamentoModel;
import com.example.AgeCare.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

   @Autowired
    private  AgendamentoRepository repository;

    
    public List< AgendamentoModel> listar(){
        return repository.findAll();
    }

    public  AgendamentoModel salvar( AgendamentoModel agendamento){
        return repository.save(agendamento);
    }

    public Optional< AgendamentoModel> buscarPorId(Long id){
        return repository.findById(id);
    }

    public AgendamentoModel atualizar(Long id,  AgendamentoModel agendamento) {
        agendamento.setId(id);
        return repository.save(agendamento);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
}

package com.example.AgeCare.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.AgeCare.model.RelatorioModel;
import com.example.AgeCare.repository.RelatorioRepository;


@Service
public class RelatorioService {

     @Autowired
    private RelatorioRepository repository;

    
    public List<RelatorioModel> listar(){
        return repository.findAll();
    }

    public RelatorioModel salvar(RelatorioModel relatorio){
        return repository.save(relatorio);
    }

    public Optional<RelatorioModel> buscarPorId(Long id){
        return repository.findById(id);
    }

    public RelatorioModel atualizar(Long id, RelatorioModel relatorio) {
        relatorio.setId(id);
        return repository.save(relatorio);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
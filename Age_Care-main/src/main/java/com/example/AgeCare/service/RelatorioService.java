package com.example.AgeCare.service;

import com.example.AgeCare.model.RelatorioModel;
import com.example.AgeCare.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    public RelatorioModel salvar(RelatorioModel relatorio){
        return repository.save(relatorio);

    }

    public List<RelatorioModel> listar(){
        return repository.findAll();
    }

    public Optional<RelatorioModel> buscarPorId(Long id){
        return repository.findById(id);

    }

    public RelatorioModel atualizar(Long id, RelatorioModel relatorio){
        relatorio.setId(id);
        return repository.save(relatorio);

    }

    public void deletar(Long id){
        repository.deleteById(id);
    }


}

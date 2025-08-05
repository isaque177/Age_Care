
package com.example.AgeCare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AgeCare.dto.AgendamentoDTO;
import com.example.AgeCare.model.AgendamentoModel;
import com.example.AgeCare.model.EnderecoModel;
import com.example.AgeCare.model.PacienteModel;
import com.example.AgeCare.model.ProfissionalModel;
import com.example.AgeCare.model.ServicoModel;
import com.example.AgeCare.model.UsuarioModel;
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

    public static Object save(AgendamentoModel agendamento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

   public AgendamentoModel converterDTOparaModel(AgendamentoDTO dto) {
    AgendamentoModel model = new AgendamentoModel();
    model.setDataInicio(dto.getDataInicio());
    model.setDataFim(dto.getDataFim());
    model.setStatus(dto.getStatus());

    ProfissionalModel profissional = new ProfissionalModel();
    profissional.setId(dto.getProfissionalId());
    model.setProfissional(profissional);

    ServicoModel servico = new ServicoModel();
    servico.setId(dto.getServicoId());
    model.setServico(servico);

    PacienteModel paciente = new PacienteModel();
    paciente.setId(dto.getIdosoId());
    model.setPaciente(paciente);

    UsuarioModel responsavel = new UsuarioModel();
    responsavel.setId(dto.getResponsavelId());
    model.setResponsavel(responsavel);

    EnderecoModel endereco = new EnderecoModel();
    endereco.setId(dto.getEnderecoId());
    model.setEndereco(endereco);

    return model;
}

    
}

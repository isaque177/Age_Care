
/* 
@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoModel repository;

    public List<AgendamentoModel> listar() {
        return repository.findAll();
    }

    public Optional<AgendamentoModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public AgendamentoModel salvar(AgendamentoModel agendamento) {
        return repository.save(agendamento);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public AgendamentoModel atualizar(Long id, AgendamentoModel agendamento) {
        repository.setId(id);
        return repository.save(agendamento);
    }
    
}
*/


/* 
@RestController
@RequestMapping("/api/agendamento")

public class AgendamentoController {

    @Autowired
    private AgendamentoModel service;


    @PostMapping
    public ResponseEntity<AgendamentoModel> salvar(AgendamentoModel agendamento){
        return ResponseEntity.ok(service.salvar(agendamento));
    }

    @GetMapping
    public ResponseEntity <List<AgendamentoModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoModel> buscarPorId(Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoModel> atualizar(Long id, AgendamentoModel agendamento) {
        return ResponseEntity.ok(service.atualizar(id, agendamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
*/
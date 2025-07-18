package com.example.AgeCare;

/* 
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.AgeCare.model.UsuarioModel;
import com.example.AgeCare.repository.UsuarioRepository;
import com.example.AgeCare.service.UsuarioService;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  
@ActiveProfiles("test")         
@TestPropertySource(locations = "classpath:application-test.properties")

class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    
 private UsuarioModel criarUsuarioFake() {
    UsuarioModel usuario = new UsuarioModel();
    usuario.setNome("Isaque Joabe");
    usuario.setEmail("Isaque@gmail.com");
    usuario.setSenhaHash("$2a$10$ExemploHashSenhaSegura"); 
    usuario.setTelefone("1122334455");
    usuario.setCelular("11987654321");
    usuario.setFotoPerfil("http://exemplo.com/foto.jpg");
    usuario.setDataNascimento(LocalDate.of(1985, 5, 20));
    usuario.setCpf("123.456.789-00");
    return usuario;
}


@Test
void contextoCarregadoECriaTabela() {
    var usuario = usuarioService.listar();
    assertNotNull(usuario);
}

 @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void limparUsuarios() {
        usuarioRepository.deleteAll();
    }


    @Test
    void testSalvarEBuscarUsuario() {
        System.out.println("[TESTE] Iniciando teste de salvar e buscar usuario...");

        UsuarioModel salvo = usuarioService.salvar(criarUsuarioFake());

        assertNotNull(salvo.getId(), "[ASSERT] O ID não deveria ser nulo");

        var buscado = usuarioService.buscarPorId(salvo.getId());
        assertTrue(buscado.isPresent(), "[ASSERT] usuario deveria existir");

        System.out.println("[TESTE] Teste salvar/buscar finalizado com sucesso!");
    }

    @Test
    void testListarUsuarios() {
        System.out.println("[TESTE] Iniciando teste de listagem...");

        usuarioService.salvar(criarUsuarioFake());

        List<UsuarioModel> lista = usuarioService.listar();
        assertTrue(lista.size() >= 2,
                   "[ASSERT] Pelo menos dois profissionais deveriam estar listados");

        System.out.println("[TESTE] Teste de listagem finalizado com sucesso!");
    }

    @Test
    void testAtualizarUsuario() {
        System.out.println("[TESTE] Iniciando teste de atualização...");

        UsuarioModel salvo = usuarioService.salvar(criarUsuarioFake());

        salvo.setCelular("31989879907");
        salvo.setEmail("teste@gmail.com");

        UsuarioModel atualizado =
                usuarioService.atualizar(salvo.getId(), salvo);

        assertEquals("31989879907", atualizado.getCelular());
        assertEquals("teste@gmail.com", atualizado.getEmail());        

    }

    @Test
    void testDeletarUsuario() {
        System.out.println("[TESTE] Iniciando teste de deleção...");

        UsuarioModel salvo = usuarioService.salvar(criarUsuarioFake());
        Long id = salvo.getId();

        usuarioService.deletar(id);
        var resultado = usuarioService.buscarPorId(id);

        assertTrue(resultado.isEmpty(), "[ASSERT] O usuario deveria ter sido deletado");
    }

    @Test
    void testBuscarUsuarioInexistente() {
        System.out.println("[TESTE] Iniciando teste de busca inválida...");

        var resultado = usuarioService.buscarPorId(-1L);
        assertTrue(resultado.isEmpty(), "[ASSERT] Nada deveria ser encontrado para ID inexistente");

    }
}
*/
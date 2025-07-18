package com.example.AgeCare;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.AgeCare.model.ProfissionalModel;
import com.example.AgeCare.model.ProfissionalModel.StatusDisponibilidade;
import com.example.AgeCare.service.ProfissionalService;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class ProfissionalServiceIntegrationTest {

    @Autowired
    private ProfissionalService profissionalService;

    private ProfissionalModel criarProfissionalFake() {
        ProfissionalModel profissional = new ProfissionalModel();
        profissional.setFormacao("Fisioterapia");
        profissional.setBiografia("Atendimento domiciliar com foco em idosos.");
        profissional.setCurriculoUrl("http://exemplo.com/curriculo.pdf");
        profissional.setNotaMedia(new BigDecimal("4.70"));
        profissional.setTotalAvaliacoes(20);
        profissional.setTotalAtendimentos(150);
        profissional.setStatusDisponibilidade(StatusDisponibilidade.ABERTO);
        return profissional;
    }
      /* FALHO */
    @Test
    void contextoCarregadoECriaTabela() {
        var profissionais = profissionalService.listar();
        assertNotNull(profissionais);
    }
      /* FALHO */
    @Test
    void testSalvarEBuscarProfissional() {
        System.out.println("[TESTE] Iniciando teste de salvar e buscar profissional...");

        ProfissionalModel salvo = profissionalService.salvar(criarProfissionalFake());

        assertNotNull(salvo.getId(), "[ASSERT] O ID não deveria ser nulo");
        assertEquals("Fisioterapia", salvo.getFormacao(), "[ASSERT] Formação incorreta");

        var buscado = profissionalService.buscarPorId(salvo.getId());
        assertTrue(buscado.isPresent(), "[ASSERT] Profissional deveria existir");
        assertEquals("Fisioterapia", buscado.get().getFormacao(),
                "[ASSERT] Formação do profissional buscado incorreta");

        System.out.println("[TESTE] Teste salvar/buscar finalizado com sucesso!");
    }

    /* FALHO */
    @Test
    void testListarProfissionais() {
        System.out.println("[TESTE] Iniciando teste de listagem...");

        profissionalService.salvar(criarProfissionalFake());
        profissionalService.salvar(criarProfissionalFake());

        List<ProfissionalModel> lista = profissionalService.listar();
        assertTrue(lista.size() >= 2,
                "[ASSERT] Pelo menos dois profissionais deveriam estar listados");

        System.out.println("[TESTE] Teste de listagem finalizado com sucesso!");
    }
    /* FALHO */
    @Test
    void testAtualizarProfissional() {
        System.out.println("[TESTE] Iniciando teste de atualização...");

        ProfissionalModel salvo = profissionalService.salvar(criarProfissionalFake());

        salvo.setFormacao("Terapia Ocupacional");
        salvo.setBiografia("Nova biografia atualizada.");

        ProfissionalModel atualizado = profissionalService.atualizar(salvo.getId(), salvo);

        assertEquals("Terapia Ocupacional", atualizado.getFormacao(),
                "[ASSERT] Formação não foi atualizada");
        assertEquals("Nova biografia atualizada.", atualizado.getBiografia(),
                "[ASSERT] Biografia não foi atualizada");

        System.out.println("[TESTE] Teste de atualização finalizado com sucesso!");
    }
     /* FALHO */
    @Test
    void testDeletarProfissional() {
        System.out.println("[TESTE] Iniciando teste de deleção...");

        ProfissionalModel salvo = profissionalService.salvar(criarProfissionalFake());
        Long id = salvo.getId();

        profissionalService.deletar(id);
        var resultado = profissionalService.buscarPorId(id);

        assertTrue(resultado.isEmpty(), "[ASSERT] O profissional deveria ter sido deletado");

        System.out.println("[TESTE] Teste de deleção finalizado com sucesso!");
    }
      /* FALHO */
    @Test
    void testBuscarProfissionalInexistente() {
        System.out.println("[TESTE] Iniciando teste de busca inválida...");

        var resultado = profissionalService.buscarPorId(-1L);
        assertTrue(resultado.isEmpty(), "[ASSERT] Nada deveria ser encontrado para ID inexistente");

        System.out.println("[TESTE] Teste de busca inválida finalizado com sucesso!");
    }
}


/* 
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.AgeCare.model.ProfissionalModel;
import com.example.AgeCare.service.ProfissionalService;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  
@ActiveProfiles("test")         
@TestPropertySource(locations = "classpath:application-test.properties")

class ProfissionalServiceIntegrationTest {

    @Autowired
    private ProfissionalService profissionalService;

    
 private ProfissionalModel criarProfissionalFake() {
    ProfissionalModel profissional = new ProfissionalModel();
    profissional.setExperienciaAnos(5);
    profissional.setFormacao("Fisioterapia"); 
    profissional.setEspecializacoes("Geriatria");
    profissional.setValorHora(new BigDecimal("90.00"));
    profissional.setValorDiaria(new BigDecimal("600.00"));
    profissional.setDisponibilidade24h(true);
    profissional.setRaioAtendimentoKm(new BigDecimal("15.0"));
    profissional.setBiografia("Atendimento domiciliar com foco em idosos.");
    profissional.setCurriculoUrl("http://exemplo.com/curriculo.pdf");
    profissional.setNotaMedia(4.7);
    profissional.setTotalAvaliacoes(20);
    profissional.setTotalAtendimentos(150);
    return profissional;
}

@Test
void contextoCarregadoECriaTabela() {
    var profissionais = profissionalService.listar();
    assertNotNull(profissionais);
}


    @Test
    void testSalvarEBuscarProfissional() {
        System.out.println("[TESTE] Iniciando teste de salvar e buscar profissional...");

        ProfissionalModel salvo = profissionalService.salvar(criarProfissionalFake());

        assertNotNull(salvo.getId(), "[ASSERT] O ID não deveria ser nulo");
        assertEquals("Fisioterapia", salvo.getFormacao(), "[ASSERT] Formação incorreta");

        var buscado = profissionalService.buscarPorId(salvo.getId());
        assertTrue(buscado.isPresent(), "[ASSERT] Profissional deveria existir");
        assertEquals("Fisioterapia", buscado.get().getFormacao(),
                     "[ASSERT] Formação do profissional buscado incorreta");

        System.out.println("[TESTE] Teste salvar/buscar finalizado com sucesso!");
    }

    @Test
    void testListarProfissionais() {
        System.out.println("[TESTE] Iniciando teste de listagem...");

        profissionalService.salvar(criarProfissionalFake());
        profissionalService.salvar(criarProfissionalFake());

        List<ProfissionalModel> lista = profissionalService.listar();
        assertTrue(lista.size() >= 2,
                   "[ASSERT] Pelo menos dois profissionais deveriam estar listados");

        

        System.out.println("[TESTE] Teste de listagem finalizado com sucesso!");
    }

    @Test
    void testAtualizarProfissional() {
        System.out.println("[TESTE] Iniciando teste de atualização...");

        ProfissionalModel salvo = profissionalService.salvar(criarProfissionalFake());

       
        salvo.setFormacao("Terapia Ocupacional");
        salvo.setExperienciaAnos(10);

        ProfissionalModel atualizado =
                profissionalService.atualizar(salvo.getId(), salvo);

        assertEquals("Terapia Ocupacional", atualizado.getFormacao(),
                     "[ASSERT] Formação não foi atualizada");
        assertEquals(10, atualizado.getExperienciaAnos(),
                     "[ASSERT] Anos de experiência não foram atualizados");

        System.out.println("[TESTE] Teste de atualização finalizado com sucesso!");
    }

    @Test
    void testDeletarProfissional() {
        System.out.println("[TESTE] Iniciando teste de deleção...");

        ProfissionalModel salvo = profissionalService.salvar(criarProfissionalFake());
        Long id = salvo.getId();

        profissionalService.deletar(id);
        var resultado = profissionalService.buscarPorId(id);

        assertTrue(resultado.isEmpty(), "[ASSERT] O profissional deveria ter sido deletado");

        System.out.println("[TESTE] Teste de deleção finalizado com sucesso!");
    }

    @Test
    void testBuscarProfissionalInexistente() {
        System.out.println("[TESTE] Iniciando teste de busca inválida...");

        var resultado = profissionalService.buscarPorId(-1L);
        assertTrue(resultado.isEmpty(), "[ASSERT] Nada deveria ser encontrado para ID inexistente");

        System.out.println("[TESTE] Teste de busca inválida finalizado com sucesso!");
    }
}
/* */
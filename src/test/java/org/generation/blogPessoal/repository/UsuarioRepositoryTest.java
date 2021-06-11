package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.service.UsuarioService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@BeforeAll
	public void start() {
		Usuario usuario = new Usuario("Carlos", "carlos@h", "123456");
		if((repository.findByUsuario("carlos@h").isEmpty())){
			service.cadastrarUsuario(usuario);
		}
		usuario = new Usuario("Pedro Carlos", "pedro@h", "123456");
		if((repository.findByUsuario("pedro@h").isEmpty())){
			service.cadastrarUsuario(usuario);
		}
		usuario = new Usuario("Carlos Henrique", "henri@h", "123456");
		if((repository.findByUsuario("henri@h").isEmpty())){
			service.cadastrarUsuario(usuario);
		}
	}
	
	@Test
	public void findByUsuario() throws Exception {
		Optional<Usuario> usuario = repository.findByUsuario("henri@h");
		assertTrue(usuario.get().getUsuario().equals("henri@h"));
	}
	
	@Test
	public void findAllByNomeIgnoreCaseRetornandoDoisUsuario() {
		List<Usuario> usuario = repository.findAllByNomeContainingIgnoreCase("carlos");
		assertEquals(3, usuario.size());
	}
	
	@AfterAll
	public void end() {
		repository.deleteAll();
	}
	
	

}

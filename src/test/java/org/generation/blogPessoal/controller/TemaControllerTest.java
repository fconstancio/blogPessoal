package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.TemaRepository;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.generation.blogPessoal.service.UsuarioService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemaControllerTest {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private TemaRepository temaRepository;
	
	private Tema tema;

	@BeforeAll
	public void start() {
		Usuario usuario = new Usuario("Carlos", "carlos@h", "123456");
		if ((usuarioRepository.findByUsuario("carlos@h").isEmpty())) {
			usuarioService.cadastrarUsuario(usuario);
		}
		tema = new Tema("POO");
	}
	
	@Test
	public void deveRealizarPostTema() {
		HttpEntity<Tema> request = new HttpEntity<>(tema);
		ResponseEntity<Tema> resposta = testRestTemplate
				.withBasicAuth("carlos@h", "123456")
				.exchange("/temas", HttpMethod.POST, request, Tema.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
	
	@Test
	public void deveMostrarTodosTemas () {
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("carlos@h", "123456")
				.exchange("/temas", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
		temaRepository.deleteAll();
		
	}

}

package br.com.alterdata.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.dto.ColaboradorDTO;
import br.com.alterdata.dto.ColaboradorResponseDTO;
import br.com.alterdata.repositories.ColaboradorRepository;
import br.com.alterdata.services.ColaboradorService;

@RestController
public class ColaboradorController {
	
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	@Autowired
	ColaboradorService colaboradorService;
	
	@GetMapping("/colaboradores")
	public ResponseEntity <List<ColaboradorDTO>> buscarTodos() {
		List<ColaboradorDTO> colaborador = colaboradorService.buscarTodosColaboradores();
		return ResponseEntity.status(HttpStatus.OK).body(colaborador);
	}
	
	@GetMapping("/colaboradoresSimples")
	public ResponseEntity <List<ColaboradorResponseDTO>> buscarTodosObjSimplificado() {
		List<ColaboradorResponseDTO> colaboradorResponseDTO	= colaboradorService.buscarTodosObjSimplificado();
		return ResponseEntity.status(HttpStatus.OK).body(colaboradorResponseDTO);
	}
	
	@GetMapping("/colaborador/{login}")
	public ResponseEntity<ColaboradorResponseDTO> buscarColaboradorPorID(@PathVariable String login) {
		ColaboradorResponseDTO colaborador = colaboradorService.buscarColaboradorPorLogin(login);
		return ResponseEntity.status(HttpStatus.OK).body(colaborador);
	}
	
	@PostMapping("/colaborador")
	public ResponseEntity<?> postColaborador(@RequestBody Colaborador colaborador) {
		colaborador = colaboradorService.inserirColaborador(colaborador);
		if(colaborador == null) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Login já cadastrado");
		}
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(colaborador.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}

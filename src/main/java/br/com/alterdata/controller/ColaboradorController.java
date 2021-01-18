package br.com.alterdata.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.dto.ColaboradorDTO;
import br.com.alterdata.dto.ColaboradorResponseDTO;
import br.com.alterdata.repositories.ColaboradorRepository;

@RestController
public class ColaboradorController {
	
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	@GetMapping("/colaboradores")
	public ResponseEntity <List<ColaboradorDTO>> buscarTodos() {
		List<Colaborador> colaboradores = colaboradorRepository.buscarTodos();
		List<ColaboradorDTO> colaboradoresDTO = colaboradores
				.stream()
				.map(x -> new ColaboradorDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(colaboradoresDTO);
	}
	
	@GetMapping("/colaboradoresSimples")
	public ResponseEntity <List<ColaboradorResponseDTO>> buscarTodosObjSimplificado() {
		List<Colaborador> colaboradores = colaboradorRepository.findAll();
		List<ColaboradorResponseDTO> colaboradorResponseDTO = colaboradores
				.stream()
				.map(x -> new ColaboradorResponseDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(colaboradorResponseDTO);
	}
	
	@GetMapping("/colaborador/{login}")
	public ResponseEntity<ColaboradorResponseDTO> buscarColaboradorPorID(@PathVariable String login) {
		Colaborador colaborador = colaboradorRepository.buscarPorLogin(login);
		ColaboradorResponseDTO colaboradorResponseDTO = new ColaboradorResponseDTO(colaborador);
				
		return ResponseEntity.status(HttpStatus.OK).body(colaboradorResponseDTO);
	}
	
	@PostMapping("/colaborador")
	public ResponseEntity<Colaborador> postColaborador(@RequestBody Colaborador colaborador) {

		Colaborador valida = colaboradorRepository.buscarPorLogin(colaborador.getLogin());
		
		if (valida == null) {
			colaboradorRepository.save(colaborador);
				return new ResponseEntity<>(colaborador, HttpStatus.CREATED);
			
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

}

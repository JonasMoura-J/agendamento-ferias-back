package br.com.alterdata.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alterdata.domain.Ferias;
import br.com.alterdata.dto.FeriasDTO;
import br.com.alterdata.dto.FeriasRequestDTO;
import br.com.alterdata.dto.FeriasResponseDTO;
import br.com.alterdata.enums.Funcao;
import br.com.alterdata.repositories.ColaboradorRepository;
import br.com.alterdata.repositories.FeriasRepository;
import br.com.alterdata.services.FeriasService;

@RestController
public class FeriasController {
	
	LocalDate dataHoje = LocalDate.now();
	
	@Autowired
	FeriasRepository feriasRepository;
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	@Autowired
	FeriasService feriasService;
	
	@GetMapping("/ferias")
	public ResponseEntity <List<FeriasResponseDTO>> findAll() {
		List<FeriasResponseDTO> feriasResponseDTO = feriasService.buscarTodasFerias();
		return ResponseEntity.status(HttpStatus.OK).body(feriasResponseDTO);
	}
	
	@GetMapping("/ferias/{duracao}")
	public ResponseEntity <List<FeriasResponseDTO>> pegarFeriasPorDuracao(@PathVariable int duracao) {	
		List<FeriasResponseDTO> feriasResponseDTO = feriasService.feriasPorDuracao(duracao);
		return ResponseEntity.status(HttpStatus.OK).body(feriasResponseDTO);
	}
	
	@GetMapping("ferias/{mes}/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorPeriodo(@PathVariable int mes,  @PathVariable int ano) {
		List<FeriasDTO> feriasDTO = feriasService.buscarPorMesEAno(mes, ano);
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}
	
	@GetMapping("ferias/ano/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorAno(@PathVariable int ano) {
		List<FeriasDTO> feriasDTO = feriasService.buscarPorAno(ano);	
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}
	
	@GetMapping("ferias/{mesinicio}/{mesFim}/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorPeriodoOsAtivos(@PathVariable int mesinicio,
			@PathVariable int mesFim, @PathVariable int ano) {
		
		List<FeriasDTO> feriasDTO = feriasService.buscarPorPeriodoOsAtivos(mesinicio, mesFim, ano);
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}
	
	@GetMapping("ferias/registro/{funcao}/{mes}/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorFucaoEPeriodo(@PathVariable Funcao funcao,
			@PathVariable int mes, @PathVariable int ano) {
		
		List<FeriasDTO> feriasDTO = feriasService.buscarPorFucaoEPeriodo(funcao, mes, ano);
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}

	@PostMapping("/ferias")
	public ResponseEntity<?> postFerias(@RequestBody FeriasRequestDTO dto) throws Exception {

		Ferias ferias = feriasService.adicionarFerias(dto);
		if (ferias == null) {		
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não foi possível cadastrar férias");
		}
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(ferias.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("ferias/{id}")
	public ResponseEntity<Ferias> AtualizarFerias(@PathVariable long id,
			@RequestBody FeriasRequestDTO dto) throws Exception {
		Ferias ferias = feriasService.atualizar(dto, id);
		return ResponseEntity.ok().body(ferias);
	}
}

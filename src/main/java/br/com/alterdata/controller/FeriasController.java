package br.com.alterdata.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.domain.Ferias;
import br.com.alterdata.dto.FeriasDTO;
import br.com.alterdata.dto.FeriasRequestDTO;
import br.com.alterdata.dto.FeriasResponseDTO;
import br.com.alterdata.enums.Funcao;
import br.com.alterdata.repositories.ColaboradorRepository;
import br.com.alterdata.repositories.FeriasRepository;

@RestController
public class FeriasController {
	
	LocalDate dataHoje = LocalDate.now();
	
	@Autowired
	FeriasRepository feriasRepository;
	
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	@GetMapping("/ferias")
	public ResponseEntity <List<FeriasResponseDTO>> findAll() {
		List<Ferias> ferias = feriasRepository.buscarTodas();
		List<FeriasResponseDTO> feriasResponseDTO = ferias
				.stream()
				.map(x -> new FeriasResponseDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(feriasResponseDTO);
	}
	
	@GetMapping("/ferias/{duracao}")
	public ResponseEntity <List<FeriasResponseDTO>> pegarFeriasPorDuracao(@PathVariable int duracao) {
		List<Ferias> ferias = feriasRepository.buscarPorDuracao(duracao);
		
		List<FeriasResponseDTO> feriasResponseDTO = ferias
				.stream()
				.map(x -> new FeriasResponseDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(feriasResponseDTO);
	}
	
	@GetMapping("ferias/{mes}/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorPeriodo(@PathVariable int mes,  @PathVariable int ano) {

		List<Ferias> ferias = feriasRepository.buscarTodas();
		
		List<Ferias> feriasPorPeriodo = new ArrayList<>();
		
		for (Ferias f : ferias) {
			boolean estahNoBanco = f.getDataInicio().getMonthValue()== mes && f.getDataInicio().getYear()== ano;
			
			if(estahNoBanco) {
				feriasPorPeriodo.add(f);
			}
		}
		List<FeriasDTO> feriasDTO = feriasPorPeriodo
				.stream()
				.map(x -> new FeriasDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}
	
	@GetMapping("ferias/ano/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorAno(@PathVariable int ano) {

		List<Ferias> ferias = feriasRepository.buscarTodas();
		
		List<Ferias> feriasPorPeriodo = new ArrayList<>();
		
		for (Ferias f : ferias) {
			if(f.getDataInicio().getYear() == ano) {
				feriasPorPeriodo.add(f);
			}
		}
		List<FeriasDTO> feriasDTO = feriasPorPeriodo
				.stream()
				.map(x -> new FeriasDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}
	
	@GetMapping("ferias/{mesinicio}/{mesFim}/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorPeriodoOsAtivos(@PathVariable int mesinicio, @PathVariable int mesFim, @PathVariable int ano) {

		List<Ferias> ferias = feriasRepository.buscarTodas();
		
		List<Ferias> feriasPorPeriodo = new ArrayList<>();
		
 		for (Ferias f : ferias) {
			if((f.getDataInicio().getMonthValue() == mesinicio || f.getDataFim().getMonthValue()== mesFim) && f.getDataInicio().getYear() == ano) {
				feriasPorPeriodo.add(f);
			}
		}
		List<FeriasDTO> feriasDTO = feriasPorPeriodo
				.stream()
				.map(x -> new FeriasDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}
	
	@GetMapping("ferias/registro/{funcao}/{mes}/{ano}")
	public ResponseEntity <List<FeriasDTO>> listarPorFucaoEPeriodo(@PathVariable Funcao funcao,
			@PathVariable int mes, @PathVariable int ano) {

		List<Ferias> ferias = feriasRepository.findAll();
		
		List<Ferias> feriasPorPeriodo = new ArrayList<>();
		
		for (Ferias f : ferias) {
			if(f.getDataInicio().getMonthValue()== mes && f.getDataInicio().getYear()== ano && f.getColaborador().getFuncao() == funcao) {
				feriasPorPeriodo.add(f);
			}
		}
		List<FeriasDTO> feriasDTO = feriasPorPeriodo
				.stream()
				.map(x -> new FeriasDTO(x))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(feriasDTO);
	}

	@PostMapping("/ferias")
	public ResponseEntity<Ferias> postFerias(@RequestBody FeriasRequestDTO dto) {

		Ferias ferias = dto.toFerias(colaboradorRepository);
		Ferias valida = feriasRepository.buscarPorId(ferias.getId());
		
		if (valida == null) {
				Colaborador colaborador = colaboradorRepository.buscarPorLogin(dto.getLogin());
				colaborador.getFerias().add(ferias);
				
				feriasRepository.save(ferias);
				
				ferias.enviarEmail(ferias.getColaborador().getNome(),
						ferias.getColaborador().getEmail(),
						ferias.getDataInicio(), ferias.getDataFim(), ferias.getDuracao());
				
				return new ResponseEntity<>(ferias, HttpStatus.CREATED);
			
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("ferias/{id}")
	public ResponseEntity<Ferias> AtualizarFerias(@PathVariable long id,@RequestBody FeriasRequestDTO dto) {
		
		Ferias feriasExistente = feriasRepository.buscarPorId(id);
		Ferias feriasAtualizada = dto.toFerias(colaboradorRepository);
		
		if(feriasExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		feriasExistente.setColaborador(feriasAtualizada.getColaborador());
		feriasExistente.setDataFim(feriasAtualizada.getDataFim());
		feriasExistente.setDataInicio(feriasAtualizada.getDataInicio());
		feriasExistente.setDuracao(feriasAtualizada.getDuracao());
		feriasExistente.setId(feriasAtualizada.getId());
		
		feriasRepository.save(feriasExistente);
		
		return ResponseEntity.ok().body(feriasExistente);
	}
}

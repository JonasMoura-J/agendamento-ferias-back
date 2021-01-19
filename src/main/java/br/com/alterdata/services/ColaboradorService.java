package br.com.alterdata.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.dto.ColaboradorDTO;
import br.com.alterdata.dto.ColaboradorResponseDTO;
import br.com.alterdata.repositories.ColaboradorRepository;

@Service
public class ColaboradorService {
	
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	public List<ColaboradorDTO> buscarTodosColaboradores() {
		List<Colaborador> colaboradores = colaboradorRepository.buscarTodos();
		List<ColaboradorDTO> colaboradoresDTO = colaboradores
				.stream()
				.map(x -> new ColaboradorDTO(x))
				.collect(Collectors.toList());
		
		return colaboradoresDTO;
	}
	
	public List<ColaboradorResponseDTO> buscarTodosObjSimplificado() {
		List<Colaborador> colaboradores = colaboradorRepository.findAll();
		List<ColaboradorResponseDTO> colaboradorResponseDTO = colaboradores
				.stream()
				.map(x -> new ColaboradorResponseDTO(x))
				.collect(Collectors.toList());
		
		return colaboradorResponseDTO;
	}
	
	public ColaboradorResponseDTO buscarColaboradorPorLogin(String login) {
		Colaborador colaborador = colaboradorRepository.buscarPorLogin(login);
		ColaboradorResponseDTO colaboradorResponseDTO = new ColaboradorResponseDTO(colaborador);
		return colaboradorResponseDTO;
	}
	
	public Colaborador inserirColaborador(Colaborador colaborador) {
		Colaborador valida = colaboradorRepository.buscarPorLogin(colaborador.getLogin());
		
		if (valida == null) {
			return colaboradorRepository.save(colaborador);
		}
		return null;
	}
}

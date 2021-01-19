package br.com.alterdata.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.domain.Ferias;
import br.com.alterdata.dto.FeriasDTO;
import br.com.alterdata.dto.FeriasRequestDTO;
import br.com.alterdata.dto.FeriasResponseDTO;
import br.com.alterdata.enums.Funcao;
import br.com.alterdata.repositories.ColaboradorRepository;
import br.com.alterdata.repositories.FeriasRepository;

@Service
public class FeriasService {
	
	@Autowired
	FeriasRepository feriasRepository;
	
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	public List<FeriasResponseDTO> buscarTodasFerias() {
		List<Ferias> ferias = feriasRepository.buscarTodas();
		List<FeriasResponseDTO> feriasResponseDTO = ferias
				.stream()
				.map(x -> new FeriasResponseDTO(x))
				.collect(Collectors.toList());
		
		return feriasResponseDTO;
	}
	
	public List<FeriasResponseDTO> feriasPorDuracao(int duracao) {
		List<Ferias> ferias = feriasRepository.buscarPorDuracao(duracao);
		
		List<FeriasResponseDTO> feriasResponseDTO = ferias
				.stream()
				.map(x -> new FeriasResponseDTO(x))
				.collect(Collectors.toList());
		
		return feriasResponseDTO;
	}
	
	public List<FeriasDTO> buscarPorMesEAno(int mes, int ano) {
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
		
		return feriasDTO;
	}
	
	public List<FeriasDTO> buscarPorAno(int ano) {
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
		return feriasDTO;
	}
	
	public List<FeriasDTO> buscarPorPeriodoOsAtivos(int mesinicio, int mesFim, int ano) {
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
		
		return feriasDTO;
	}
	
	public List<FeriasDTO> buscarPorFucaoEPeriodo(Funcao funcao, int mes, int ano) {
		List<Ferias> ferias = feriasRepository.findAll();
		List<Ferias> feriasPorPeriodo = new ArrayList<>();

		for (Ferias f : ferias) {
			if(f.getDataInicio().getMonthValue()== mes
					&& f.getDataInicio().getYear()== ano
					&& f.getColaborador().getFuncao() == funcao) {
				feriasPorPeriodo.add(f);
			}
		}
		List<FeriasDTO> feriasDTO = feriasPorPeriodo
				.stream()
				.map(x -> new FeriasDTO(x))
				.collect(Collectors.toList());

		return feriasDTO;
	}
	
	public Ferias adicionarFerias(FeriasRequestDTO dto) throws Exception {

		Ferias ferias = dto.toFerias(colaboradorRepository);
		Ferias valida = feriasRepository.buscarPorId(ferias.getId());
		
		if (valida != null) {
			throw new Exception("O login já existe");
		}
		Colaborador colaborador = colaboradorRepository.buscarPorLogin(dto.getLogin());
		colaborador.getFerias().add(ferias);
		feriasRepository.save(ferias);
		
		ferias.enviarEmail(ferias.getColaborador().getNome(),
				ferias.getColaborador().getEmail(),
				ferias.getDataInicio(), ferias.getDataFim(), ferias.getDuracao());
		
		return ferias;
	}
	
	public Ferias atualizar(FeriasRequestDTO dto, long id) throws Exception {
		Ferias feriasExistente = feriasRepository.buscarPorId(id);
		Ferias feriasAtualizada = dto.toFerias(colaboradorRepository);
		
		if(feriasExistente == null) {
			throw new Exception("usuario não encontrado");
		}
		
		feriasExistente.setColaborador(feriasAtualizada.getColaborador());
		feriasExistente.setDataFim(feriasAtualizada.getDataFim());
		feriasExistente.setDataInicio(feriasAtualizada.getDataInicio());
		feriasExistente.setDuracao(feriasAtualizada.getDuracao());
		feriasExistente.setId(feriasAtualizada.getId());
		
		return feriasRepository.save(feriasExistente);
	}
}

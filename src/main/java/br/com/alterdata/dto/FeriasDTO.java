package br.com.alterdata.dto;

import java.time.LocalDate;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.domain.Ferias;

public class FeriasDTO {
	
	private long id;
	
	private LocalDate dataInicio;

	private LocalDate dataFim;
	
	private int duracao;
	
	private Colaborador colaborador;
	
	@SuppressWarnings("unused")
	private FeriasDTO() {
		
	}
	
	public FeriasDTO(long id, LocalDate dataInicio, LocalDate dataFim, int duracao, Colaborador colaborador) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.duracao = duracao;
		this.colaborador = colaborador;
	}

	
	public FeriasDTO(Ferias ferias) {
		super();
		this.id = ferias.getId();
		this.dataInicio = ferias.getDataInicio();
		this.dataFim = ferias.getDataFim();
		this.duracao = ferias.getDuracao();
		this.colaborador = ferias.getColaborador();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
}

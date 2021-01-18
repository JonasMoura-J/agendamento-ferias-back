package br.com.alterdata.dto;

import java.time.LocalDate;

import br.com.alterdata.domain.Ferias;

public class FeriasResponseDTO {
	
	private long id;
	
	private LocalDate dataInicio;

	private LocalDate dataFim;
	
	private int duracao;
	
	@SuppressWarnings("unused")
	private FeriasResponseDTO() {
		
	}
	
	public FeriasResponseDTO(long id, LocalDate dataInicio, LocalDate dataFim, int duracao) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.duracao = duracao;
	}

	
	public FeriasResponseDTO(Ferias ferias) {
		super();
		this.id = ferias.getId();
		this.dataInicio = ferias.getDataInicio();
		this.dataFim = ferias.getDataFim();
		this.duracao = ferias.getDuracao();
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
}

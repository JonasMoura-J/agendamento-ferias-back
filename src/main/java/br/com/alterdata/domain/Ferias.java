package br.com.alterdata.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.alterdata.utils.SendMail;

@Entity
@Table(name="TB_FERIAS")
public class Ferias implements Serializable, Comparable<Ferias>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private LocalDate dataInicio;

	@NotNull
	private LocalDate dataFim;
	
	@NotNull
	private int duracao;
	
	@ManyToOne
	@JsonIgnore
	@JoinTable (
				name = "ferias_colaborador",
				joinColumns = @JoinColumn(name = "colaborador_id", referencedColumnName =  "id"),
				inverseJoinColumns = @JoinColumn(name = "ferias_id", referencedColumnName = "id")
				)
	@NotNull
	private Colaborador colaborador;
	
	public Ferias() {
		
	}

	public Ferias(long id, LocalDate dataInicio, LocalDate dataFim, int duracao, Colaborador colaborador) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.duracao = duracao;
		this.colaborador = colaborador;
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

	@Override
	public int compareTo(Ferias o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void enviarEmail(String nome, String email, LocalDate inicio, LocalDate fim, int duracao) {
		
		String mensagem = "Olá " + nome + ",\r\n"
				+ "\r\n"
				+ "Temos uma ótima notícia!\r\n"
				+ "As suas FÉRIAS foram agendadas para a data de " + inicio + " com a duração de " + duracao + " dias, com o término para o dia " + fim + ". Espero que aproveite e descanse bastante neste período.\r\n"
				+ "\r\n"
				+ "Atenciosamente,\r\n"
				+ "RH Alterdata Software";
		
		SendMail mail = new SendMail();
	
		
		    try {
		        mail.sendMail("agendadorferias@gmail.com", email , "Aviso de Agendamento de férias!", mensagem);
		    } catch (Exception e) {
		       e.printStackTrace();
		    }
		}
}

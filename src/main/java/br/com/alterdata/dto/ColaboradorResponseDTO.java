package br.com.alterdata.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.alterdata.domain.Colaborador;

public class ColaboradorResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String login;
	
	private String nome;
	
	private String email;
	
	private LocalDate dataAdmissao;
	
	private LocalDate aniversarioDataContratacao;
	
	@SuppressWarnings("unused")
	private ColaboradorResponseDTO() {
		
	}
	
	public ColaboradorResponseDTO(Colaborador colaborador) {
		super();
		this.id = colaborador.getId();
		this.login = colaborador.getLogin();
		this.nome = colaborador.getNome();
		this.email = colaborador.getEmail();
		this.dataAdmissao = colaborador.getDataAdmissao();
		this.aniversarioDataContratacao = colaborador.getAniversarioDataContratacao();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public LocalDate getAniversarioDataContratacao() {
		return aniversarioDataContratacao;
	}

	public void setAniversarioDataContratacao(LocalDate aniversarioDataContratacao) {
		this.aniversarioDataContratacao = aniversarioDataContratacao;
	}

}

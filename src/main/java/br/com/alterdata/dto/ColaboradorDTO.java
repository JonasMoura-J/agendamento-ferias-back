package br.com.alterdata.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.domain.Ferias;

public class ColaboradorDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String login;
	
	private String nome;
	
	private String email;
	
	@ElementCollection(targetClass=Ferias.class)
	private List<Ferias> ferias = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private ColaboradorDTO() {
		
	}
	
	public ColaboradorDTO(Colaborador colaborador) {
		super();
		this.id = colaborador.getId();
		this.login = colaborador.getLogin();
		this.nome = colaborador.getNome();
		this.email = colaborador.getEmail();
		this.ferias = colaborador.getFerias();
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

	public List<Ferias> getFerias() {
		return ferias;
	}

	public void setFerias(List<Ferias> ferias) {
		this.ferias = ferias;
	}

}

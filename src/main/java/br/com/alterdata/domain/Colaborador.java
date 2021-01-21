package br.com.alterdata.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.alterdata.enums.Funcao;

@Entity
@Table(name="TB_COLABORADOR")
public class Colaborador implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String login;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String email;
	
	@NotNull
	private LocalDate dataAdmissao;
	
	@NotNull
	private String departamento;
	
	@NotNull
	private LocalDate aniversarioDataContratacao = dataAdmissao;
	
	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Funcao funcao;

	@ElementCollection(targetClass=Ferias.class)
	private List<Ferias> ferias = new ArrayList<>();

	public Colaborador() {
		
	}

	public Colaborador(long id, @NotNull String login, @NotNull String nome, @NotNull String email,
			@NotNull LocalDate dataAdmissao, @NotNull String departamento,
			@NotNull LocalDate aniversarioDataContratacao, @NotNull Funcao funcao, List<Ferias> ferias) {
		super();
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.email = email;
		this.dataAdmissao = dataAdmissao;
		this.departamento = departamento;
		this.aniversarioDataContratacao = aniversarioDataContratacao;
		this.funcao = funcao;
		this.ferias = ferias;
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

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public List<Ferias> getFerias() {
		return ferias;
	}

	public void setFerias(List<Ferias> ferias) {
		this.ferias = ferias;
	}

	public LocalDate getAniversarioDataContratacao() {
		return aniversarioDataContratacao;
	}

	public void setAniversarioDataContratacao(LocalDate aniversarioDataContratacao) {
		this.aniversarioDataContratacao = aniversarioDataContratacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colaborador other = (Colaborador) obj;
		if (id != other.id)
			return false;
		return true;
	}


}

package br.com.alterdata.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import br.com.alterdata.domain.Colaborador;
import br.com.alterdata.domain.Ferias;
import br.com.alterdata.repositories.ColaboradorRepository;
import javassist.NotFoundException;

public class FeriasRequestDTO {
	
	private long id;
	
	private LocalDate dataInicio;

	private LocalDate dataFim;
	
	private int duracao;
	
	private String login;
	
	public Ferias toFerias(ColaboradorRepository repository) throws NotFoundException {

		Colaborador contemColaborador = repository.buscarPorLogin(login);
		
		if (contemColaborador == null) {
			throw new NotFoundException("O login não existe");
		}
		
		int periodo = Period.between(LocalDate.now(), this.dataInicio).getDays();

		if (periodo <= 0) {

			throw new HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE,
					"Não é possivel agendar férias para o dia atual ou em datas anteriores");
		}
		
		Colaborador colaborador = contemColaborador;
		
		LocalDate dataCadastro = LocalDate.of(this.dataInicio.getYear(),
				this.dataInicio.getMonth(), this.dataInicio.getDayOfMonth());
		
		LocalDate dataFim = LocalDate.of(this.dataFim.getYear(),
				this.dataFim.getMonth(), this.dataFim.getDayOfMonth());
		
		long dias = dataCadastro.until(dataFim, ChronoUnit.DAYS)+1;
		
		LocalDate admissao = LocalDate.of(colaborador.getDataAdmissao().getYear(),
				colaborador.getDataAdmissao().getMonth(), colaborador.getDataAdmissao().getDayOfMonth());
		
		long meses = admissao.until(dataCadastro, ChronoUnit.MONTHS);
		
		Collections.sort(colaborador.getFerias());
		
		if (dias == this.duracao) {
			if (meses >= 12 && meses < 24) {
				if (colaborador.getFerias().isEmpty()) {
					Ferias ferias = new Ferias(this.id, this.dataInicio, this.dataFim, this.duracao, colaborador);
					if (this.duracao == 30) {
						colaborador.setAniversarioDataContratacao(colaborador.getDataAdmissao().plusYears(1));
					}
					return ferias;
					
				} else if (colaborador.getFerias().size() == 1) {
					int verificacao = Period.between(colaborador.getFerias()
							.get(colaborador.getFerias()
							.size()-1)
							.getDataFim(), this.dataInicio)
							.getDays();
					
					if (colaborador.getFerias().get(colaborador.getFerias().size()-1).getDuracao() == 15
							&& this.duracao == 15 
							&& verificacao >= 0) {
						
						Ferias ferias = new Ferias(this.id, this.dataInicio, this.dataFim, this.duracao, colaborador);
						
						colaborador.setAniversarioDataContratacao(colaborador.getDataAdmissao().plusYears(1));
						
						return ferias;
					} else {
						throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
								"Não pode cadastrar as férias no período de uma férias ativa");
					}
				} else {
					throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
							"O Colaborador não possui férias disponiveis");
				}
				
			}else if (meses > 24) {
				LocalDate aniversario = LocalDate.of(colaborador.getAniversarioDataContratacao().getYear(),
						colaborador.getAniversarioDataContratacao().getMonth(),
						colaborador.getAniversarioDataContratacao().getDayOfMonth());
				
				long periodoMeses = aniversario.until(dataCadastro, ChronoUnit.MONTHS);
				
				if (periodoMeses >= 12) {
					if (this.getDuracao() == 30) {
						
						Ferias ferias = new Ferias(this.id, this.dataInicio, this.dataFim, this.duracao,colaborador);
						colaborador.setAniversarioDataContratacao(colaborador.
								getAniversarioDataContratacao().plusYears(1));
						return ferias;
						
					} else if (this.getDuracao() == 15) {
						LocalDate ultimaFerias = colaborador.getFerias()
								.get(colaborador.getFerias().size()-1).getDataInicio();
						
						LocalDate periodoUltimaFerias = LocalDate.of(ultimaFerias.getYear(),
								ultimaFerias.getMonth(),
								ultimaFerias.getDayOfMonth());
						
						long periodoMesesUltimaFerias = periodoUltimaFerias.until(dataCadastro, ChronoUnit.MONTHS);
						
						int verificacao = Period.between(colaborador.getFerias()
								.get(colaborador.getFerias()
								.size()-1)
								.getDataFim(), this.dataInicio)
								.getDays();
						
						if (periodoMesesUltimaFerias < 12 && verificacao > 0) {
							Ferias ferias = new Ferias(this.id, this.dataInicio, this.dataFim, this.duracao, colaborador);
							
							colaborador.setAniversarioDataContratacao
								(colaborador.getAniversarioDataContratacao().plusYears(1));
							
							return ferias;
							
						} else {
							Ferias ferias = new Ferias(this.id, this.dataInicio, this.dataFim, this.duracao, colaborador);
							return ferias;
						}
					}
				} else {
					throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
							"Ainda não completaram 12 meses para a sua proxima férias");
				}
			} else if(meses < 12) {
				throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
						"O Colaborador ainda não possui 1 ano na empresa");
			}
        } else {
        	throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
        			"As datas não condizem com a duração definida");
        }
		return null;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
}

package br.com.alterdata.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alterdata.domain.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{
	
	@Query("FROM Colaborador")
	List<Colaborador> buscarTodos();
	
	@Query("SELECT a FROM Colaborador a WHERE a.login = ?1")
	Colaborador buscarPorLogin(String login);

}

package br.com.alterdata.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alterdata.domain.Ferias;

public interface FeriasRepository extends JpaRepository<Ferias, Long>{
	
	@Query("FROM Ferias")
	List<Ferias> buscarTodas();
	
	@Query("SELECT a FROM Ferias a WHERE a.duracao = ?1")
	List<Ferias> buscarPorDuracao(int duracao);
	
	@Query("SELECT a FROM Ferias a WHERE a.id = ?1")
	Ferias buscarPorId(long id);
}

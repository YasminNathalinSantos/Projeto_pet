package br.com.fiap.projeto_pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.projeto_pet.model.PrioridadeEnum;
import br.com.fiap.projeto_pet.model.TipoCuidado;

public interface TipoCuidadoRepository extends JpaRepository<TipoCuidado, Long> {

	// JPQL: buscar por prioridade
	@Query("from TipoCuidado tc where tc.prioridade = :prioridade order by tc.nome asc")
	List<TipoCuidado> buscarPorPrioridade(PrioridadeEnum prioridade);

}

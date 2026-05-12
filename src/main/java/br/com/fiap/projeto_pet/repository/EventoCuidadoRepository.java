package br.com.fiap.projeto_pet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.projeto_pet.model.EventoCuidado;
import br.com.fiap.projeto_pet.model.StatusEventoEnum;
import br.com.fiap.projeto_pet.projection.EventoProjection;

public interface EventoCuidadoRepository extends JpaRepository<EventoCuidado, Long> {

	// JPQL: buscar eventos por status
	@Query("from EventoCuidado e where e.status = :status order by e.dataPrevista asc")
	List<EventoCuidado> buscarPorStatus(StatusEventoEnum status);

	// JPQL: listar eventos atrasados (status ATRASADO)
	@Query("from EventoCuidado e where e.status = 'ATRASADO' order by e.dataPrevista asc")
	List<EventoCuidado> listarAtrasados();

	// JPQL: listar próximos cuidados de um pet específico (PENDENTE, ordenado por data)
	@Query("from EventoCuidado e where e.pet.id = :petId and e.status = 'PENDENTE' order by e.dataPrevista asc")
	List<EventoCuidado> listarProximosCuidadosPorPet(Long petId);

	// Native SQL: buscar eventos por período com projection
	@Query(nativeQuery = true,
		   value = "select distinct p.nome pet_nome, tc.nome tipo_cuidado_nome, "
		   		 + "e.data_prevista evento_data_prevista, e.status evento_status, "
		   		 + "tc.prioridade tipo_prioridade "
		   		 + "from evento_cuidado e "
		   		 + "inner join pet p on (e.fk_pet = p.id) "
		   		 + "inner join tipo_cuidado tc on (e.fk_tipo_cuidado = tc.id) "
		   		 + "where e.data_prevista between :dataInicio and :dataFim "
		   		 + "order by e.data_prevista asc")
	List<EventoProjection> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);

	// Native SQL: buscar eventos por status com projection ordenado por prioridade
	@Query(nativeQuery = true,
		   value = "select distinct p.nome pet_nome, tc.nome tipo_cuidado_nome, "
		   		 + "e.data_prevista evento_data_prevista, e.status evento_status, "
		   		 + "tc.prioridade tipo_prioridade "
		   		 + "from evento_cuidado e "
		   		 + "inner join pet p on (e.fk_pet = p.id) "
		   		 + "inner join tipo_cuidado tc on (e.fk_tipo_cuidado = tc.id) "
		   		 + "where e.status = :status "
		   		 + "order by tc.prioridade asc, e.data_prevista asc")
	List<EventoProjection> buscarPorStatusProjection(String status);

}

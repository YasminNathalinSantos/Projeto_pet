package br.com.fiap.projeto_pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.projeto_pet.model.EspecieEnum;
import br.com.fiap.projeto_pet.model.Pet;
import br.com.fiap.projeto_pet.projection.PetProjection;

public interface PetRepository extends JpaRepository<Pet, Long> {

	// JPQL: buscar pets por espécie
	@Query("from Pet p where p.especie = :especie order by p.nome asc")
	List<Pet> buscarPorEspecie(EspecieEnum especie);

	// Native SQL: buscar por substring (nome do pet, raça ou nome do tutor) com projection
	@Query(nativeQuery = true,
		   value = "select distinct p.nome pet_nome, p.especie pet_especie, "
		   		 + "p.raca pet_raca, t.nome tutor_nome "
		   		 + "from pet p "
		   		 + "inner join tutor t on (p.fk_tutor = t.id) "
		   		 + "where (UPPER(p.nome) LIKE UPPER('%' || :substring || '%')) "
		   		 + "or (UPPER(p.raca) LIKE UPPER('%' || :substring || '%')) "
		   		 + "or (UPPER(t.nome) LIKE UPPER('%' || :substring || '%')) "
		   		 + "order by p.nome asc")
	List<PetProjection> buscarPorSubstring(String substring);

	// Native SQL: buscar por raça
	@Query(nativeQuery = true,
		   value = "select distinct p.nome pet_nome, p.especie pet_especie, "
		   		 + "p.raca pet_raca, t.nome tutor_nome "
		   		 + "from pet p "
		   		 + "inner join tutor t on (p.fk_tutor = t.id) "
		   		 + "where UPPER(p.raca) LIKE UPPER('%' || :raca || '%') "
		   		 + "order by p.nome asc")
	List<PetProjection> buscarPorRaca(String raca);

}

package br.com.fiap.projeto_pet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.fiap.projeto_pet.model.EspecieEnum;
import br.com.fiap.projeto_pet.model.Pet;
import br.com.fiap.projeto_pet.projection.PetProjection;
import br.com.fiap.projeto_pet.repository.PetRepository;

@Service
public class PetCachingService {

	@Autowired
	private PetRepository repP;

	@Cacheable(value = "todosPets")
	public List<Pet> findAll() {
		return repP.findAll();
	}

	@Cacheable(value = "petPorID", key = "#id")
	public Optional<Pet> findById(Long id) {
		return repP.findById(id);
	}

	@Cacheable(value = "petsPorPaginacao", key = "#pr")
	public Page<Pet> findAll(PageRequest pr) {
		return repP.findAll(pr);
	}

	@Cacheable(value = "petsPorSubstring", key = "#substring")
	public List<PetProjection> buscarPorSubstring(String substring) {
		return repP.buscarPorSubstring(substring);
	}

	@Cacheable(value = "petsPorEspecie", key = "#especie")
	public List<Pet> buscarPorEspecie(EspecieEnum especie) {
		return repP.buscarPorEspecie(especie);
	}

	@CacheEvict(value = { "todosPets", "petPorID", "petsPorPaginacao",
			"petsPorSubstring", "petsPorEspecie" }, allEntries = true)
	public void removerCache() {
		System.out.println("Removendo cache de pets");
	}

}

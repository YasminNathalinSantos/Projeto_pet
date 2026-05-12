package br.com.fiap.projeto_pet.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_pet.dto.PetDTO;
import br.com.fiap.projeto_pet.model.EspecieEnum;
import br.com.fiap.projeto_pet.model.Pet;
import br.com.fiap.projeto_pet.projection.PetProjection;
import br.com.fiap.projeto_pet.repository.PetRepository;
import br.com.fiap.projeto_pet.service.PetCachingService;
import br.com.fiap.projeto_pet.service.PetPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pets")
public class PetController {

	@Autowired
	private PetRepository repP;

	@Autowired
	private PetPaginacaoService paginacaoP;

	@Autowired
	private PetCachingService cacheP;

	@Operation(description = "Retorna todos os pets em forma de páginas de PetDTO",
			   summary = "Retornar páginas de PetDTO",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/paginados")
	public ResponseEntity<Page<PetDTO>> paginar(
		@RequestParam(name = "page", defaultValue = "0") Integer page,
		@RequestParam(name = "size", defaultValue = "2") Integer size,
		@RequestParam(name = "ordenarPor", defaultValue = "nome") String ordenarPor) {
		PageRequest pr = PageRequest.of(page, size, Sort.by(ordenarPor).ascending());
		Page<PetDTO> paginados = paginacaoP.paginar(pr);
		return ResponseEntity.ok(paginados);
	}

	@Operation(description = "Retorna todos os pets (via cache)",
			   summary = "Retornar todos os pets (caching)",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/todos")
	public List<Pet> retornarTodos() {
		return cacheP.findAll();
	}

	@Operation(description = "Retorna um pet pelo ID",
			   summary = "Retornar pet por ID",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/{id}")
	public Pet retornarPorId(@PathVariable Long id) {
		Optional<Pet> op = cacheP.findById(id);
		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado com id: " + id);
		}
	}

	@Operation(description = "Busca pets por substring no nome, raça ou nome do tutor (via cache)",
			   summary = "Buscar pets por substring (caching)",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/substring_caching")
	public List<PetProjection> buscarPorSubstringCaching(@RequestParam String substring) {
		return cacheP.buscarPorSubstring(substring);
	}

	@Operation(description = "Busca pets por substring no nome, raça ou nome do tutor",
			   summary = "Buscar pets por substring",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/substring")
	public List<PetProjection> buscarPorSubstring(@RequestParam String substring) {
		return repP.buscarPorSubstring(substring);
	}

	@Operation(description = "Busca pets por espécie (via cache, JPQL otimizado)",
			   summary = "Buscar pets por espécie (otimizado)",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/especie_otimizado")
	public List<Pet> buscarPorEspecieOtimizado(@RequestParam EspecieEnum especie) {
		return cacheP.buscarPorEspecie(especie);
	}

	@Operation(description = "Busca pets por espécie",
			   summary = "Buscar pets por espécie",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/especie")
	public List<Pet> buscarPorEspecie(@RequestParam EspecieEnum especie) {
		List<Pet> todos = cacheP.findAll();
		return todos.stream()
				.filter(p -> p.getEspecie() == especie)
				.toList();
	}

	@Operation(description = "Busca pets por raça",
			   summary = "Buscar pets por raça",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/raca")
	public List<PetProjection> buscarPorRaca(@RequestParam String raca) {
		return repP.buscarPorRaca(raca);
	}

	@Operation(description = "Insere um novo pet",
			   summary = "Inserir novo pet",
			   tags = "Persistência de Informações")
	@PostMapping(value = "/inserir")
	public Pet inserirPet(@RequestBody @Valid Pet pet) {
		repP.save(pet);
		cacheP.removerCache();
		return pet;
	}

	@Operation(description = "Remove um pet pelo ID",
			   summary = "Remover pet",
			   tags = "Remoção de Informações")
	@DeleteMapping(value = "/{id}")
	public Pet removerPet(@PathVariable Long id) {
		Optional<Pet> op = cacheP.findById(id);
		if (op.isPresent()) {
			repP.delete(op.get());
			cacheP.removerCache();
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado com id: " + id);
		}
	}

	@Operation(description = "Atualiza um pet pelo ID",
			   summary = "Atualizar pet",
			   tags = "Atualização de Informações")
	@PutMapping(value = "/{id}")
	public Pet atualizarPet(@PathVariable Long id, @RequestBody @Valid Pet pet) {
		Optional<Pet> op = cacheP.findById(id);
		if (op.isPresent()) {
			Pet petBanco = op.get();
			petBanco.transferirPet(pet);
			repP.save(petBanco);
			cacheP.removerCache();
			return petBanco;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado com id: " + id);
		}
	}

}

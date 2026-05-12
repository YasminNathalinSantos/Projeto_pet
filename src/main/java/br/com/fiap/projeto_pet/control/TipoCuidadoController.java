package br.com.fiap.projeto_pet.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
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

import br.com.fiap.projeto_pet.model.PrioridadeEnum;
import br.com.fiap.projeto_pet.model.TipoCuidado;
import br.com.fiap.projeto_pet.repository.TipoCuidadoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tipos_cuidado")
public class TipoCuidadoController {

	@Autowired
	private TipoCuidadoRepository repTC;

	@Operation(description = "Retorna todos os tipos de cuidado (via cache)",
			   summary = "Listar todos os tipos de cuidado (caching)",
			   tags = "Retorno de Informações")
	@GetMapping("/todos")
	@Cacheable(value = "todosTiposCuidado")
	public List<TipoCuidado> listarTodos() {
		return repTC.findAll();
	}

	@Operation(description = "Retorna um tipo de cuidado pelo ID",
			   summary = "Buscar tipo de cuidado por ID",
			   tags = "Retorno de Informações")
	@GetMapping("/{id}")
	public TipoCuidado buscarPorId(@PathVariable Long id) {
		return repTC.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de cuidado não encontrado com id: " + id));
	}

	@Operation(description = "Busca tipos de cuidado por prioridade (JPQL)",
			   summary = "Buscar tipos de cuidado por prioridade",
			   tags = "Retorno de Informações")
	@GetMapping("/prioridade")
	public List<TipoCuidado> buscarPorPrioridade(@RequestParam PrioridadeEnum prioridade) {
		return repTC.buscarPorPrioridade(prioridade);
	}

	@Operation(description = "Insere um novo tipo de cuidado",
			   summary = "Inserir novo tipo de cuidado",
			   tags = "Persistência de Informações")
	@PostMapping("/inserir")
	@CacheEvict(value = "todosTiposCuidado", allEntries = true)
	public TipoCuidado inserirTipoCuidado(@RequestBody @Valid TipoCuidado tipoCuidado) {
		repTC.save(tipoCuidado);
		return tipoCuidado;
	}

	@Operation(description = "Atualiza um tipo de cuidado pelo ID",
			   summary = "Atualizar tipo de cuidado",
			   tags = "Atualização de Informações")
	@PutMapping("/{id}")
	@CacheEvict(value = "todosTiposCuidado", allEntries = true)
	public TipoCuidado atualizarTipoCuidado(@PathVariable Long id, @RequestBody @Valid TipoCuidado tipoCuidado) {
		TipoCuidado tcBanco = repTC.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de cuidado não encontrado com id: " + id));
		tcBanco.setNome(tipoCuidado.getNome());
		tcBanco.setDescricao(tipoCuidado.getDescricao());
		tcBanco.setIntervaloDias(tipoCuidado.getIntervaloDias());
		tcBanco.setPrioridade(tipoCuidado.getPrioridade());
		repTC.save(tcBanco);
		return tcBanco;
	}

	@Operation(description = "Remove um tipo de cuidado pelo ID",
			   summary = "Remover tipo de cuidado",
			   tags = "Remoção de Informações")
	@DeleteMapping("/{id}")
	@CacheEvict(value = "todosTiposCuidado", allEntries = true)
	public TipoCuidado removerTipoCuidado(@PathVariable Long id) {
		TipoCuidado tc = repTC.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de cuidado não encontrado com id: " + id));
		repTC.deleteById(id);
		return tc;
	}

}

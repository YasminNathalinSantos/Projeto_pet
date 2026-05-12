package br.com.fiap.projeto_pet.control;

import java.time.LocalDate;
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

import br.com.fiap.projeto_pet.dto.EventoCuidadoDTO;
import br.com.fiap.projeto_pet.model.EventoCuidado;
import br.com.fiap.projeto_pet.model.StatusEventoEnum;
import br.com.fiap.projeto_pet.projection.EventoProjection;
import br.com.fiap.projeto_pet.repository.EventoCuidadoRepository;
import br.com.fiap.projeto_pet.service.EventoCachingService;
import br.com.fiap.projeto_pet.service.EventoPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/eventos")
public class EventoCuidadoController {

	@Autowired
	private EventoCuidadoRepository repE;

	@Autowired
	private EventoPaginacaoService paginacaoE;

	@Autowired
	private EventoCachingService cacheE;

	@Operation(description = "Retorna todos os eventos em forma de páginas de EventoCuidadoDTO",
			   summary = "Retornar páginas de EventoCuidadoDTO",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/paginados")
	public ResponseEntity<Page<EventoCuidadoDTO>> paginar(
		@RequestParam(name = "page", defaultValue = "0") Integer page,
		@RequestParam(name = "size", defaultValue = "3") Integer size,
		@RequestParam(name = "ordenarPor", defaultValue = "dataPrevista") String ordenarPor) {
		PageRequest pr = PageRequest.of(page, size, Sort.by(ordenarPor).ascending());
		Page<EventoCuidadoDTO> paginados = paginacaoE.paginar(pr);
		return ResponseEntity.ok(paginados);
	}

	@Operation(description = "Retorna todos os eventos (via cache)",
			   summary = "Retornar todos os eventos (caching)",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/todos")
	public List<EventoCuidado> retornarTodos() {
		return cacheE.findAll();
	}

	@Operation(description = "Retorna um evento pelo ID",
			   summary = "Retornar evento por ID",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/{id}")
	public EventoCuidado retornarPorId(@PathVariable Long id) {
		Optional<EventoCuidado> op = cacheE.findById(id);
		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado com id: " + id);
		}
	}

	@Operation(description = "Lista todos os eventos com status ATRASADO (via cache)",
			   summary = "Listar eventos atrasados (caching)",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/atrasados")
	public List<EventoCuidado> listarAtrasados() {
		return cacheE.listarAtrasados();
	}

	@Operation(description = "Lista os próximos cuidados pendentes de um pet específico",
			   summary = "Listar próximos cuidados por pet",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/proximos_cuidados")
	public List<EventoCuidado> listarProximosCuidados(@RequestParam Long petId) {
		return repE.listarProximosCuidadosPorPet(petId);
	}

	@Operation(description = "Busca eventos por status com projection e ordenação por prioridade (via cache)",
			   summary = "Buscar eventos por status (otimizado, caching)",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/status_otimizado")
	public List<EventoProjection> buscarPorStatusOtimizado(@RequestParam StatusEventoEnum status) {
		return cacheE.buscarPorStatusProjection(status);
	}

	@Operation(description = "Busca eventos por status",
			   summary = "Buscar eventos por status",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/status")
	public List<EventoCuidado> buscarPorStatus(@RequestParam StatusEventoEnum status) {
		return repE.buscarPorStatus(status);
	}

	@Operation(description = "Busca eventos por período de datas com projection",
			   summary = "Buscar eventos por período",
			   tags = "Retorno de Informações")
	@GetMapping(value = "/periodo")
	public List<EventoProjection> buscarPorPeriodo(
		@RequestParam LocalDate dataInicio,
		@RequestParam LocalDate dataFim) {
		return repE.buscarPorPeriodo(dataInicio, dataFim);
	}

	@Operation(description = "Insere um novo evento de cuidado",
			   summary = "Inserir novo evento",
			   tags = "Persistência de Informações")
	@PostMapping(value = "/inserir")
	public EventoCuidado inserirEvento(@RequestBody @Valid EventoCuidado evento) {
		repE.save(evento);
		cacheE.removerCache();
		return evento;
	}

	@Operation(description = "Remove um evento de cuidado pelo ID",
			   summary = "Remover evento",
			   tags = "Remoção de Informações")
	@DeleteMapping(value = "/{id}")
	public EventoCuidado removerEvento(@PathVariable Long id) {
		Optional<EventoCuidado> op = cacheE.findById(id);
		if (op.isPresent()) {
			repE.delete(op.get());
			cacheE.removerCache();
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado com id: " + id);
		}
	}

	@Operation(description = "Atualiza um evento de cuidado pelo ID",
			   summary = "Atualizar evento",
			   tags = "Atualização de Informações")
	@PutMapping(value = "/{id}")
	public EventoCuidado atualizarEvento(@PathVariable Long id, @RequestBody @Valid EventoCuidado evento) {
		Optional<EventoCuidado> op = cacheE.findById(id);
		if (op.isPresent()) {
			EventoCuidado eventoBanco = op.get();
			eventoBanco.transferirEvento(evento);
			repE.save(eventoBanco);
			cacheE.removerCache();
			return eventoBanco;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado com id: " + id);
		}
	}

}

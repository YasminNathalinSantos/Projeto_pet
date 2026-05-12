package br.com.fiap.projeto_pet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.fiap.projeto_pet.model.EventoCuidado;
import br.com.fiap.projeto_pet.model.StatusEventoEnum;
import br.com.fiap.projeto_pet.projection.EventoProjection;
import br.com.fiap.projeto_pet.repository.EventoCuidadoRepository;

@Service
public class EventoCachingService {

	@Autowired
	private EventoCuidadoRepository repE;

	@Cacheable(value = "todosEventos")
	public List<EventoCuidado> findAll() {
		return repE.findAll();
	}

	@Cacheable(value = "eventoPorID", key = "#id")
	public Optional<EventoCuidado> findById(Long id) {
		return repE.findById(id);
	}

	@Cacheable(value = "eventosPorPaginacao", key = "#pr")
	public Page<EventoCuidado> findAll(PageRequest pr) {
		return repE.findAll(pr);
	}

	@Cacheable(value = "eventosAtrasados")
	public List<EventoCuidado> listarAtrasados() {
		return repE.listarAtrasados();
	}

	@Cacheable(value = "eventosPorStatus", key = "#status")
	public List<EventoProjection> buscarPorStatusProjection(StatusEventoEnum status) {
		return repE.buscarPorStatusProjection(status.name());
	}

	@CacheEvict(value = { "todosEventos", "eventoPorID", "eventosPorPaginacao",
			"eventosAtrasados", "eventosPorStatus" }, allEntries = true)
	public void removerCache() {
		System.out.println("Removendo cache de eventos");
	}

}

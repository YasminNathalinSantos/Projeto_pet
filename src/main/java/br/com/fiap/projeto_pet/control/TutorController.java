package br.com.fiap.projeto_pet.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_pet.model.Tutor;
import br.com.fiap.projeto_pet.repository.TutorRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tutores")
public class TutorController {

	@Autowired
	private TutorRepository repT;

	@Operation(description = "Retorna todos os tutores",
			   summary = "Listar todos os tutores",
			   tags = "Retorno de Informações")
	@GetMapping("/todos")
	public List<Tutor> listarTodos() {
		return repT.findAll();
	}

	@Operation(description = "Retorna um tutor pelo ID",
			   summary = "Buscar tutor por ID",
			   tags = "Retorno de Informações")
	@GetMapping("/{id}")
	public Tutor buscarPorId(@PathVariable Long id) {
		return repT.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor não encontrado com id: " + id));
	}

	@Operation(description = "Insere um novo tutor",
			   summary = "Inserir novo tutor",
			   tags = "Persistência de Informações")
	@PostMapping("/inserir")
	public Tutor inserirTutor(@RequestBody @Valid Tutor tutor) {
		repT.save(tutor);
		return tutor;
	}

	@Operation(description = "Atualiza um tutor pelo ID",
			   summary = "Atualizar tutor",
			   tags = "Atualização de Informações")
	@PutMapping("/{id}")
	public Tutor atualizarTutor(@PathVariable Long id, @RequestBody @Valid Tutor tutor) {
		Tutor tutorBanco = repT.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor não encontrado com id: " + id));
		tutorBanco.setNome(tutor.getNome());
		tutorBanco.setCpf(tutor.getCpf());
		tutorBanco.setEmail(tutor.getEmail());
		tutorBanco.setTelefone(tutor.getTelefone());
		tutorBanco.setDataCadastro(tutor.getDataCadastro());
		repT.save(tutorBanco);
		return tutorBanco;
	}

	@Operation(description = "Remove um tutor pelo ID",
			   summary = "Remover tutor",
			   tags = "Remoção de Informações")
	@DeleteMapping("/{id}")
	public Tutor removerTutor(@PathVariable Long id) {
		Tutor tutor = repT.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor não encontrado com id: " + id));
		repT.deleteById(id);
		return tutor;
	}

}

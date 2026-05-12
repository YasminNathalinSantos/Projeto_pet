package br.com.fiap.projeto_pet.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_pet.model.Usuario;
import br.com.fiap.projeto_pet.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repU;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/todos")
	public List<Usuario> listarTodos() {
		return repU.findAll();
	}

	@GetMapping("/{id}")
	public Usuario buscarPorId(@PathVariable Long id) {
		return repU.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/novo")
	public Usuario inserirUsuario(@RequestBody Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		repU.save(usuario);
		return usuario;
	}

	@DeleteMapping("/remover/{id}")
	public Usuario removerUsuario(@PathVariable Long id) {
		Usuario usuario = repU.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		repU.deleteById(id);
		return usuario;
	}

}

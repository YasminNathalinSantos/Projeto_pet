package br.com.fiap.projeto_pet.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.projeto_pet.security.JWTUtil;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping(value = "/login")
	public String logar(@RequestParam String usuario,
			            @RequestParam String senha,
			            @RequestParam(value = "duracao", defaultValue = "10") Integer duracao) {
		try {
			var autenticacao = new UsernamePasswordAuthenticationToken(usuario, senha);
			manager.authenticate(autenticacao);
			return jwtUtil.gerarToken(usuario, duracao);
		} catch (Exception e) {
			return "Credenciais inválidas!";
		}
	}

}

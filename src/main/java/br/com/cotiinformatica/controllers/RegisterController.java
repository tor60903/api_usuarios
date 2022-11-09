package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.RegisterPostDto;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.services.UsuarioService;
import io.swagger.annotations.ApiOperation;

@RestController
public class RegisterController {

	@Autowired
	UsuarioService usuarioService;

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para cadastro de conta de acesso de usuário")
	@PostMapping("/api/register")
	public ResponseEntity<String> post(@RequestBody RegisterPostDto dto) {

		try {

			Usuario usuario = new Usuario();

			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setSenha(dto.getSenha());

			usuarioService.cadastrar(usuario);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Usuário " + usuario.getNome() + ", cadastrado com sucesso.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}

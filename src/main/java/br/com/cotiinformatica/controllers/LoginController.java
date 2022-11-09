package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.LoginPostDto;
import br.com.cotiinformatica.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Autenticação de Usuário")
@RestController
public class LoginController {

	@Autowired
	UsuarioService usuarioService;

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para autenticação de usuário na API e geração de TOKEN JWT.")

	@PostMapping("/api/login")
	public ResponseEntity<String> post(@RequestBody LoginPostDto dto) {

		try {

			// autenticando e gerando o token do usuário
			String token = usuarioService.autenticar(dto.getEmail(), dto.getSenha());

			return ResponseEntity.status(HttpStatus.OK).body(token);
		}

		catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}

package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.PasswordPostDto;
import br.com.cotiinformatica.services.UsuarioService;
import io.swagger.annotations.ApiOperation;

@RestController
public class PasswordController {

	@Autowired
	UsuarioService usuarioService;

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para recuperação da senha de acesso do usuário")
	@PostMapping("/api/password")
	public ResponseEntity<String> post(@RequestBody PasswordPostDto dto) {

		try {

			usuarioService.recuperarSenha(dto.getEmail());

			return ResponseEntity.status(HttpStatus.OK)
					.body("Um email de recuperação de senha foi enviado para: " + dto.getEmail() + " com sucesso!");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}

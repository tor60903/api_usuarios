package br.com.cotiinformatica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.LoginPostDto;
import br.com.cotiinformatica.dtos.PasswordPostDto;
import br.com.cotiinformatica.dtos.RegisterPostDto;

@SpringBootTest
@AutoConfigureMockMvc
class Projetoapi02ApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void postRegisterTest() throws Exception {

		Faker faker = new Faker(new Locale("pt-BR"));

		RegisterPostDto dto = new RegisterPostDto();
		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSenha(faker.internet().password(8, 10));

		mockMvc.perform(
				post("/api/register").contentType("application/json").content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isCreated());
	}

	@Test
	public void postLoginTest() throws Exception {

		Faker faker = new Faker(new Locale("pt-BR"));

		// Criando o usuário na API
		RegisterPostDto dtoRegister = new RegisterPostDto();
		dtoRegister.setNome(faker.name().fullName());
		dtoRegister.setEmail(faker.internet().emailAddress());
		dtoRegister.setSenha(faker.internet().password(8, 10));

		mockMvc.perform(post("/api/register").contentType("application/json")
				.content(objectMapper.writeValueAsString(dtoRegister)));

		// autenticando o usuário na API
		LoginPostDto dtoLogin = new LoginPostDto();
		dtoLogin.setEmail(dtoRegister.getEmail());
		dtoLogin.setSenha(dtoRegister.getSenha());

		mockMvc.perform(
				post("/api/login").contentType("application/json").content(objectMapper.writeValueAsString(dtoLogin)))
				.andExpect(status().isOk());
	}

	@Test
	public void postPasswordTest() throws Exception {

		Faker faker = new Faker(new Locale("pt-BR"));

		// Criando o usuário na API
		RegisterPostDto dtoRegister = new RegisterPostDto();
		dtoRegister.setNome(faker.name().fullName());
		dtoRegister.setEmail(faker.internet().emailAddress());
		dtoRegister.setSenha(faker.internet().password(8, 10));

		mockMvc.perform(post("/api/register").contentType("application/json")
				.content(objectMapper.writeValueAsString(dtoRegister)));

		// Recuperando a senha do usuário
		PasswordPostDto dtoPassword = new PasswordPostDto();
		dtoPassword.setEmail(dtoRegister.getEmail());

		mockMvc.perform(post("/api/password").contentType("application/json")
				.content(objectMapper.writeValueAsString(dtoPassword))).andExpect(status().isOk());
	}

}

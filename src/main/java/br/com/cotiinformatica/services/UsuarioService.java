package br.com.cotiinformatica.services;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import br.com.cotiinformatica.components.EmailComponent;
import br.com.cotiinformatica.components.MD5Component;
import br.com.cotiinformatica.components.TokenComponent;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	EmailComponent emailComponent;

	@Autowired
	MD5Component md5Component;

	@Autowired
	TokenComponent tokenComponent;

	// método para realizar a autenticação do usuário e geração do token
	public String autenticar(String email, String senha) throws Exception {

		// consultar o usuário no banco de dados
// através do email e da senha
		Usuario usuario = usuarioRepository.findByEmailAndSenha(email, md5Component.getMD5(senha));

		// verificar se o usuário não foi encontrado
		if (usuario == null) {
			throw new IllegalArgumentException("Acesso negado, usuário não encontrado.");
		}

		// gerar e retornar o token do usuário
		return tokenComponent.createToken(usuario.getEmail());
	}

	// método para gravar um usuário no banco de dados
	public void cadastrar(Usuario usuario) throws Exception {

		// Regra 1: O sistema não pode cadastrar usuários com o mesmo email
		if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
			throw new IllegalArgumentException("O email informado já está cadastrado, tente outro.");
		}

		// Regra 2: Criptografar a senha do usuário
		usuario.setSenha(md5Component.getMD5(usuario.getSenha()));

		// cadastrando no banco de dados
		usuarioRepository.save(usuario);

		// enviando um email de boas vindas para o usuário
		String assunto = "Parabéns, sua conta foi criada com sucesso (API Usuários)";
		String corpo = "Olá " + usuario.getNome()
				+ "\n\nSua conta de usuário foi criada + com sucesso em nosso sistema."
				+ "\n\nAtt\nEquipe COTI Informática";

		emailComponent.enviarMensagem(usuario.getEmail(), assunto, corpo);
	}

	// método para realizar a recuperação da senha do usuário
	public void recuperarSenha(String email) throws Exception {

		// consultar o usuário no banco de dados através do email
		Usuario usuario = usuarioRepository.findByEmail(email);

		// verificar se nenhum usuário foi encontrado
		if (usuario == null) {
			throw new IllegalArgumentException("Usuário não encontrado, por favor verifique o email informado.");
		}

		// gerar uma nova senha para o usuário
		Faker faker = new Faker(new Locale("pt-BR"));
		String novaSenha = faker.internet().password(8, 10);

		// criando uma mensagem para o usuário
		String assunto = "Parabéns, uma nova senha foi gerada com sucesso (API Usuários)";
		String corpo = "Olá " + usuario.getNome() + "\n\nUtilize a senha: " + novaSenha
				+ " para acessar sua conta no sistema." + "\n\nAtt\nEquipe COTI Informática";

		// enviando a senha nova para o email do usuário
		emailComponent.enviarMensagem(usuario.getEmail(), assunto, corpo);

		// atualizar a senha do usuário no banco de dados
		usuario.setSenha(md5Component.getMD5(novaSenha));

		// atualizando o usuário no banco de dados
		usuarioRepository.save(usuario);
	}
}

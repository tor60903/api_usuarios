package br.com.cotiinformatica.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String from;

	public void enviarMensagem(String dest, String assunto, String corpo) throws Exception {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(dest);
		mailMessage.setSubject(assunto);
		mailMessage.setText(corpo);

		//javaMailSender.send(mailMessage);
	}
}

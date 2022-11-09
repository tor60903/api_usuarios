package br.com.cotiinformatica.components;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component
public class MD5Component {

	// método para criptografar a senha em MD5
	public String getMD5(String valor) throws Exception {

		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(valor.getBytes()));

		return hash.toString(16);
	}

}

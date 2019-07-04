package com.tech.pro.backend.apirest.services;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String correo, String subject, String texto) throws MessagingException {
	
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		message.setContent(texto, "text/html; charset=utf-8");
		helper.setFrom(from);
		helper.setTo(correo);
		helper.setSubject(subject);

		javaMailSender.send(message);

	}
	
	public void sendEmailMore(List<String> arg_correos, String subject, String texto) throws MessagingException {
		String correos = String.join(",", arg_correos);
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		message.setContent(texto, "text/html; charset=utf-8");
		helper.setFrom(from);
		helper.setTo(correos);
		helper.setSubject(subject);

		javaMailSender.send(message);

	}

}

package com.buzone.vault.infrastructure;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void send(String from, String to, String subject, String text) throws Exception {
		try {
			MimeMessage mail = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);

			javaMailSender.send(mail);
		} catch (Exception exception) {
			throw exception;
		}
	}
}
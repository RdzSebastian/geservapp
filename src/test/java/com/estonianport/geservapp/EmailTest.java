package com.estonianport.geservapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.email.EmailBody;
import com.estonianport.geservapp.email.EmailService;

@SpringBootTest
class EmailTest {

	@Autowired
	EmailService emailService;

	@Test
	void testEmail() {
		EmailBody emailBody = new EmailBody();
		emailBody.setContent("Cuerpo del Email");
		emailBody.setSubject("Mail prueba GESERVAPP");
		emailBody.setEmail("rdzsebastian@gmail.com");

		emailService.sendEmail(emailBody);
	}

}

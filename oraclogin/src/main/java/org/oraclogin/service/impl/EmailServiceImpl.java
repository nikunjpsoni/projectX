
package org.oraclogin.service.impl;

import org.oraclogin.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage email) {
		try{
			mailSender.send(email);
			System.out.println("Message Send..........");
		}
		catch (MailException ex) {
            System.out.println(ex.getMessage());
        }
	}

}

package rha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import rha.util.Mail;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(final Mail mail){
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setSubject(mail.getAsunto());
        correo.setText(mail.getContenido());
        correo.setTo(mail.getPara());
        correo.setFrom(mail.getDe());

        emailSender.send(correo);
    }
}

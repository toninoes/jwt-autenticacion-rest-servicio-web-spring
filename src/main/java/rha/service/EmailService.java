package rha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import rha.jwt.model.security.ActivacionUsuario;
import rha.util.Mail;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
    private String remitente;
	
	@Value("${protocolo}")
	private String protocolo;
	
	@Value("${dominio}")
	private String dominio;
	
	@Value("${puerto}")
	private String puerto;

    public void enviarEmailPersonalizado(final Mail mail){
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setSubject(mail.getAsunto());
        correo.setText(mail.getContenido());
        correo.setTo(mail.getPara());
        correo.setFrom(mail.getDe());

        javaMailSender.send(correo);
    }

	public void enviarCorreoActivacion(ActivacionUsuario activacionUsuario) {
		SimpleMailMessage correo = new SimpleMailMessage();
		correo.setSubject("Activación de usuario");
		
		String contenido = "ACTIVACION DE USUARIO\n\n";
		contenido += "Vaya a: " + protocolo + dominio + puerto;
		contenido += "/activacion/" + activacionUsuario.getTokenActivacion();
				
		correo.setText(contenido);
		correo.setTo(activacionUsuario.getUser().getEmail());
        correo.setFrom(remitente);
		
		javaMailSender.send(correo);		
	}
    
    
}

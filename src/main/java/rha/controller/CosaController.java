package rha.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rha.service.EmailService;
import rha.util.Mail;

@RestController
@RequestMapping("/api/cosas")
public class CosaController {
	
	private static Logger log = LoggerFactory.getLogger(CosaController.class);
	
	@Autowired
    private EmailService emailService;
	
	@GetMapping
	public String findAll() {
		log.info("Enviando Correo de Prueba");

        Mail mail = new Mail();
        mail.setDe("toninoes.dev@gmail.com");
        mail.setPara("toninoes@gmail.com");
        mail.setAsunto("Correo de prueba");
        mail.setContenido("Hola");

        emailService.sendSimpleMessage(mail);
        
		return "Hola, Ha funcionado!!!";
	}
	
}

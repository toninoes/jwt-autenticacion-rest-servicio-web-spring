package rha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cosas")
public class CosaController {
	
	@GetMapping
	public String findAll() {        
		return "Hola, Ha funcionado!!!";
	}
	
}

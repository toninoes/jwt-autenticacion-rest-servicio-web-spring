package rha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rha.model.Cosa;
import rha.repository.CosaRepository;

@RestController
@RequestMapping("/api/cosas")
public class CosaController {
	
	@Autowired
	private CosaRepository cosaRepository;
	
	@GetMapping
	public String findAll() {
		return "Hola, Ha funcionado!!!";
	}
	
}

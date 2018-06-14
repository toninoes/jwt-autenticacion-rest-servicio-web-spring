package rha.jwt.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rha.jwt.model.security.ActivacionUsuario;
import rha.jwt.model.security.Authority;
import rha.jwt.model.security.AuthorityName;
import rha.jwt.model.security.User;
import rha.jwt.security.JwtUser;
import rha.jwt.security.JwtUserFactory;
import rha.jwt.security.repository.ActivacionUsuarioRepository;
import rha.jwt.security.repository.AuthorityRepository;
import rha.jwt.security.repository.UserRepository;

@RestController
@RequestMapping("admin")
public class MethodProtectedRestController {
	
	@Autowired 
	private AuthorityRepository authRep;
	
	@Autowired
	private ActivacionUsuarioRepository actUsrRep;
	
	@Autowired
	private UserRepository usrRep;
	
	@Autowired
	private PasswordEncoder pass;
	
	@GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProtectedGreeting() {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
	@GetMapping("sanitario")
    @PreAuthorize("hasRole('SANITARIO')")
    public ResponseEntity<JwtUser> holaKillo() {
		List<Authority> roles = new ArrayList<Authority>();
		roles.add(authRep.findByName(AuthorityName.ROLE_PACIENTE));
		User u = new User("antonisfddskko", "password", "Antonio", "Ruiz", "aruissss@xxx.es", true, new Date(), roles);
		usrRep.save(u);
		
        return ResponseEntity.ok(JwtUserFactory.create(u));
    }
	
	@PostMapping("registroAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<JwtUser> registroAdmin(@Valid @RequestBody User user) {		
		try {
			return registro(user);
		} catch (Exception e) {
			throw new RegistroException("Error al registrar el usuario.", e);
		}		
	}
	
	@PostMapping("registroSanitario")
	@PreAuthorize("hasRole('SANITARIO')")
	public ResponseEntity<JwtUser> registroSanitario(@Valid @RequestBody User user) {		
		try {
			if(puedeRegistrar(user.getAuthorities())) {
				return registro(user);
			} else {
				Exception e = new Exception("Usted sólo puede registrar pacientes.");
				throw new RegistroException("Error al registrar el usuario.", e);
			}
		} catch (Exception e) {
			throw new RegistroException("Error al registrar el usuario.", e);
		}
	}
	
	/**
	 * Los sanitarios sólo pueden registrar a pacientes.
	 * 
	 * @param authorities
	 * @return
	 */
	private boolean puedeRegistrar(List<Authority> authorities) {
		return (authorities.size() == 1 && 
				authorities.get(0).getName() == AuthorityName.ROLE_PACIENTE);
	}

	private ResponseEntity<JwtUser> registro(User user) {
		
		/*Authority rolAdmin = new Authority(AuthorityName.ROLE_ADMIN);
		Authority rolSanitario = new Authority(AuthorityName.ROLE_SANITARIO);
		Authority rolPaciente = new Authority(AuthorityName.ROLE_PACIENTE);
		
		authRep.save(rolAdmin);
		authRep.save(rolSanitario);
		authRep.save(rolPaciente);
		
		List<Authority> roles = new ArrayList<Authority>();
		
		roles.add(rolPaciente);
		User paciente = new User("paciente2", pass.encode("paciente2"), "Paciente2", "Paciente2", 
				"paciente2@user.es", true, new Date(), roles);
		usrRep.save(paciente);
		ActivacionUsuario actU = new ActivacionUsuario(paciente);
		actUsrRep.save(actU);

		
		return ResponseEntity.ok(JwtUserFactory.create(paciente));*/
		Authority rolAdmin = authRep.findByName(AuthorityName.ROLE_ADMIN);
		Authority rolSanitario = authRep.findByName(AuthorityName.ROLE_SANITARIO);
		Authority rolPaciente = authRep.findByName(AuthorityName.ROLE_PACIENTE);
		
		List<Authority> roles = new ArrayList<Authority>();

		if (user.getPermisos().get(0))
			roles.add(rolAdmin);
		if (user.getPermisos().get(1))
			roles.add(rolSanitario);
		if (user.getPermisos().get(2))
			roles.add(rolPaciente);
		
		
		User u = new User(user.getUsername(), pass.encode(user.getEmail()),
				user.getFirstname(), user.getLastname(), user.getEmail(),
				false, new Date(), roles);
		usrRep.save(u);
		
		ActivacionUsuario actU = new ActivacionUsuario(u);
		actUsrRep.save(actU);
		
		return ResponseEntity.ok(JwtUserFactory.create(u));
		
		
	}
	
	
	

}

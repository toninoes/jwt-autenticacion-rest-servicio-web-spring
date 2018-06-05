package rha.jwt.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rha.jwt.model.security.Authority;
import rha.jwt.model.security.AuthorityName;
import rha.jwt.model.security.User;
import rha.jwt.security.JwtUser;
import rha.jwt.security.JwtUserFactory;
import rha.jwt.security.repository.AuthorityRepository;
import rha.jwt.security.repository.UserRepository;

@RestController
@RequestMapping("admin")
public class MethodProtectedRestController {
	
	@Autowired 
	private AuthorityRepository authRep;
	
	@Autowired UserRepository usrRep;

    /**
     * This is an example of some different kinds of granular restriction for endpoints. You can use the built-in SPEL expressions
     * in @PreAuthorize such as 'hasRole()' to determine if a user has access. Remember that the hasRole expression assumes a
     * 'ROLE_' prefix on all role names. So 'ADMIN' here is actually stored as 'ROLE_ADMIN' in database!
     **/
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
	

}

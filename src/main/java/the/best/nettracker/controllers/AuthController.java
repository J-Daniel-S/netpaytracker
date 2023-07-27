package the.best.nettracker.controllers;

import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import the.best.nettracker.documents.User;
import the.best.nettracker.exceptions.UserNotFoundException;
import the.best.nettracker.services.AuthService;

@RestController
@RequestMapping("/wagetrak-login")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS })
public class AuthController {
	
	@Autowired
	private AuthService aServ;
	
	
	
	@PostMapping
	public ResponseEntity<Map<Object, Object>> login(@RequestHeader(value = "Authorization") String req) throws Throwable {
		String[] decoded = aServ.splitHeader(req);
		String username = decoded[0];
		String password = decoded[1];
		try {
			User user = aServ.findUser(username);
			try {
				aServ.authenticate(username, password);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
			}
			Map<Object, Object> model = aServ.buildLoginReponse(user);
			return ResponseEntity.ok(model);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<Object, Object>>  regiser(@RequestHeader(value = "Authorization")String req) throws Throwable {
		String[] decoded = aServ.splitHeader(req);
		Optional<User> possibleUser = aServ.buildOptionalUser(decoded);
		if (!possibleUser.isPresent()) {
			Map<Object, Object> model = aServ.registerBuildReponse(possibleUser, decoded);
			return ResponseEntity.ok(model);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}

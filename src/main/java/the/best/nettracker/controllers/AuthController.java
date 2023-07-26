package the.best.nettracker.controllers;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import the.best.nettracker.documents.User;
import the.best.nettracker.exceptions.BadCredentialsException;
import the.best.nettracker.exceptions.UserNotFoundException;
import the.best.nettracker.services.UserService;

@RestController
@RequestMapping("/wagetrak-login")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS })
public class AuthController {
	
	@Autowired
	private UserService usRepo;
	
	private final Decoder decoder = Base64.getDecoder();
	
	@PostMapping
	@SuppressWarnings("rawtypes")
	public ResponseEntity login(@RequestHeader(value = "Authorization") String req) throws Throwable {
		String[] decoded = splitAndDecode(req);
		String username = decoded[0];
		String password = decoded[1];
		try {
			User user = usRepo.findByUserName(username).orElseThrow(() -> new UserNotFoundException("User not found by username"));
			
			if (!user.getPassword().equals(password)) {
				//placeholder exception.  Replace with org.spring...security.
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
			}
			
			String id = user.getId();
			
			String token = "token";
			
			Map<Object, Object> model = new HashMap<>();
			model.put("UserId", id);
			model.put("token", "Bearer " + token);
					
			return ResponseEntity.ok(model);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		}
		
	}
	
	@PostMapping("/register")
	@SuppressWarnings("rawtypes")
	public ResponseEntity regiser(@RequestHeader(value = "Authorization")String req) throws Throwable {
		String[] decoded = splitAndDecode(req);
		String username = decoded[0];
		Double taxRate = Integer.parseInt(decoded[1]) / 100.0;
		String password = decoded[2];
		
		User user = new User(username, taxRate, password);
		Optional<User> possibleUser = usRepo.findByUserName(user.getUserName());
		
		if (!possibleUser.isPresent()) {
			usRepo.save(user);
			Map<Object, Object> model = new HashMap<>();
			model.put("message", "User registered");

			return ResponseEntity.ok(model);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	private String[] splitAndDecode(String req) {
		String[] creds = req.split(" ");
		String credentials = creds[1];
		String credString = new String(decoder.decode(credentials));
		String[] decoded = credString.split(":");
		return decoded;
	}

}

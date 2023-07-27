package the.best.nettracker.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import the.best.nettracker.documents.User;
import the.best.nettracker.exceptions.UserNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private UserService usRepo;
	
	@Autowired
	private JwtService jServ;
	
	@Autowired BCryptPasswordEncoder encoder;
	
	@Autowired AuthenticationManager manager;
	
	public Map<Object, Object> buildLoginReponse(User user) {
		String id = user.getId();
		
		//by this point we're certain that the user exists, findUser() has already been run
		String token = jServ.generateToken(usRepo.findById(id).get());
		
		Map<Object, Object> model = new HashMap<>();
		model.put("UserId", id);
		model.put("token", "Bearer " + token);
		
		return model;
	}
	
	public Map<Object, Object> registerBuildReponse(Optional<User> possibleUser, String[] req) {
		String username = req[0];
		Double taxRate = Integer.parseInt(req[1]) / 100.0;
		String password = req[2];
		User user = new User(username, taxRate, encoder.encode(password));
		usRepo.save(user);
		Map<Object, Object> model = new HashMap<>();
		model.put("message", "User registered");
		return model;
	}
	
	public String[] splitHeader(String req) {
		String credentials = req.substring(6);
		System.out.println(credentials);
		return credentials.split(":");
	}
	
	public Optional<User> buildOptionalUser(String[] req) {
		String username = req[0];
		User user = new User(username);
		return usRepo.findByUsername(user.getUsername());
	}

	public User findUser(String username) throws UserNotFoundException {
		return usRepo.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found by username"));
	}
	
	public void authenticate(String id, String password) throws Exception {
		manager.authenticate(new UsernamePasswordAuthenticationToken(id, password));
	}
	
}

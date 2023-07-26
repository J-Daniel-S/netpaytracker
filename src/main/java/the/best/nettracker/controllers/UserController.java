package the.best.nettracker.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import the.best.nettracker.documents.User;
import the.best.nettracker.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET,
		RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RequestMapping("/wagetrak/users")
public class UserController {

	@Autowired
	private UserService usRepo;
	
	private String userFindErrorMsg = "Cannot find user by given ID";

	// for unit testing
	public UserController(UserService usRepo) {
		this.usRepo = usRepo;
	}

	@PostMapping
	// this method is for testing. If you want to use it add the test user from the
	// readme using advanced rest client or something similar
	public User addUser(@RequestBody User newUser) {
		User user = newUser;
		boolean saved = usRepo.save(user);
		if (saved) {
			return user;
		} else {
			throw new RuntimeException("Cannot Add User");
		}

	}

	@GetMapping("/{id}")
	@ResponseBody
	public User getUser(@PathVariable final String id) {
		Optional<User> user = usRepo.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new RuntimeException(userFindErrorMsg);
		}

	}

	@GetMapping
	// Admin only
	public List<User> getUsers() {
		return usRepo.findAll();
	}

	@PutMapping
	public User updateUser(@RequestBody User user) {
		String id = user.getId();
		Optional<User> theUser = usRepo.findById(id);
		if (theUser.isPresent()) {
			User newUser = theUser.get();
			newUser.setName(user.getName());
			newUser.setTaxRate(user.getTaxRate());
			usRepo.update(newUser);
			User returnUser = usRepo.findById(id).get();
			return returnUser;
		} else {
			throw new RuntimeException(userFindErrorMsg);
		}

	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable final String id) {
		boolean status = usRepo.delete(id);
		if (status) {
			Map<Object, Object> model = new HashMap<>();
			model.put("message", "User successfully deleted");
			return ResponseEntity.ok(model);
		} else {
			throw new RuntimeException(userFindErrorMsg);
		}
	}

}
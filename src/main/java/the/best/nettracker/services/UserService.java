package the.best.nettracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import the.best.nettracker.dao.UserRepository;
import the.best.nettracker.documents.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository usRepo;

	public UserService(UserRepository usRepo) {
		this.usRepo = usRepo;
	}

	public List<User> findAll() {
		return usRepo.findAll();
	}

	public Optional<User> findById(String id) {
		Optional<User> foundUser = usRepo.findById(id);
		return foundUser;
	}

	public Optional<User> findByUsername(String username) {
		Optional<User> foundUser = usRepo.findByUsername(username.toLowerCase());
		return foundUser;
	}

	public boolean save(User user) {
		user.setUsername(user.getUsername().toLowerCase());
		Optional<User> findUser = usRepo.findById(user.getId());
		if (findUser.isPresent()) {
			return false;
		} else {
			usRepo.insert(user);
			return true;
		}

	}

	public boolean update(User user) {
		if (usRepo.existsById(user.getId())) {
			usRepo.deleteById(user.getId());
			usRepo.insert(user);
			return true;
		} else {
			return false;
		}
	}

	public boolean delete(String id) {
		if (usRepo.existsById(id)) {
			usRepo.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found by username"));
	
	}

}
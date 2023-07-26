package the.best.nettracker.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import the.best.nettracker.documents.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public List<User> findAll();

	@Query("{ '_id': ?0 }")
	public Optional<User> findById(String id);

	@Query("{ 'userName': ?0 }")
	public Optional<User> findByUserName(String username);

	@Query("{ 'username': ?0, 'password': ? }")
	public Optional<User> login(String username, String password);

//	public Optional<User> findByToken(String token);

}

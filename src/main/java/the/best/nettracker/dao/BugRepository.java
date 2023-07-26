package the.best.nettracker.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import the.best.nettracker.documents.BugReport;

@Repository
public interface BugRepository extends MongoRepository<BugReport, String> {

	@Query("{ '_id': ?0 }")
	public Optional<BugReport> findById(String id);

}

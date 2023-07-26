package the.best.nettracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the.best.nettracker.dao.BugRepository;
import the.best.nettracker.documents.BugReport;

@Service
public class BugService {

	@Autowired
	private BugRepository bugRepo;

	public BugReport findById(String id) {
		Optional<BugReport> report = bugRepo.findById(id);
		if (report.isPresent()) {
			return report.get();
		} else {
			return new BugReport(null, "not such report");
		}
	}

	public void save(BugReport report) {
		bugRepo.insert(report);
	}

}

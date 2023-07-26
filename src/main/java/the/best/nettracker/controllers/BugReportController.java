package the.best.nettracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import the.best.nettracker.documents.BugReport;
import the.best.nettracker.services.BugService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = RequestMethod.POST)
@RequestMapping("/wagetrak-bugs")
public class BugReportController {

	// for testing
	public BugReportController(BugService bugRepo) {
		this.bugRepo = bugRepo;
	}

	@Autowired
	private BugService bugRepo;

	@PostMapping
	public HttpStatus saveReport(@RequestBody BugReport report) {
		bugRepo.save(report);
		return HttpStatus.CREATED;
	}

}

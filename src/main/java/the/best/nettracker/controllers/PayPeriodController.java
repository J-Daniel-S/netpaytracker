package the.best.nettracker.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import the.best.nettracker.documents.User;
import the.best.nettracker.documents.branches.Job;
import the.best.nettracker.documents.branches.PayPeriod;
import the.best.nettracker.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders="*" , methods = { RequestMethod.POST, RequestMethod.GET,
		RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("/wagetrak/{id}/{jobName}")
public class PayPeriodController {

	@Autowired
	private UserService usRepo;

	// for testing
	public PayPeriodController(UserService usRepo) {
		this.usRepo = usRepo;
	}

	@PostMapping
	@ResponseBody
	public User addPeriod(@PathVariable final String id, @PathVariable final String jobName,
			@RequestBody PayPeriod period) {
		Optional<User> foundUser = usRepo.findById(id);
		User user = foundUser.get();
		Job job = user.getJobs().stream().filter(j -> j.getName().equalsIgnoreCase(jobName)).findAny().get();
		if (!job.payPeriodExists(period)) {
			job.addPayPeriod(period);
			usRepo.update(user);
			return user;
		} else {
			// perhaps I must needs return something else here (ask about on stack overflow)
			return foundUser.get();
		}

	}

	@DeleteMapping("/{dateName}")
	@ResponseBody
	public User deletePeriod(@PathVariable final String id, @PathVariable final String jobName,
			@PathVariable final String dateName) {
		Optional<User> foundUser = usRepo.findById(id);
		User user = foundUser.get();
		Job job = user.getJobs().stream().filter(j -> j.getName().equalsIgnoreCase(jobName)).findAny().get();
		job.deletePayPeriod(dateName);
		usRepo.update(user);
		foundUser = usRepo.findById(id);
		return foundUser.get();
	}

	@PutMapping("/{oldDateName}")
	@ResponseBody
	public User updatePeriod(@PathVariable final String id, @PathVariable final String jobName,
			@RequestBody PayPeriod payPeriod, @PathVariable String oldDateName) {
		Optional<User> foundUser = usRepo.findById(id);
		User user = foundUser.get();
		Job job = user.getJobs().stream().filter(j -> j.getName().equalsIgnoreCase(jobName)).findAny().get();
		if (job.payPeriodExists(oldDateName)) {
			job.updatePayPeriod(payPeriod, oldDateName);
			usRepo.update(user);
			return user;
		} else {
			return foundUser.get();
		}
	}

}
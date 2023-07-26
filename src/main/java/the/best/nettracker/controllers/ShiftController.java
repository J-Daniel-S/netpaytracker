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
import the.best.nettracker.documents.branches.Shift;
import the.best.nettracker.services.UserService;

@RequestMapping("/wagetrak/{id}/{jobName}/{dateName}")
@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.POST, RequestMethod.GET,
		RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class ShiftController {

	@Autowired
	private UserService usRepo;

	// for testing
	public ShiftController(UserService usRepo) {
		this.usRepo = usRepo;
	}

	@PostMapping
	@ResponseBody
	public User addShift(@PathVariable final String id, @PathVariable final String jobName,
			@PathVariable final String dateName, @RequestBody Shift shift) {
		Optional<User> foundUser = usRepo.findById(id);
		User user = foundUser.get();
		Job job = user.getJobs().stream().filter(j -> j.getName().equalsIgnoreCase(jobName)).findAny().get();

		PayPeriod period = job.getPayPeriods().stream().filter(w -> w.getDateName().equals(dateName)).findAny().get();
		if (period.shiftNotExist(shift)) {
			shift.calcPay(job.getRate(), user.getTaxRate());
			period.addShift(shift);
			period.updatePay();
			job.updatePayPeriod(period);
			usRepo.update(user);
			return user;
		} else {
			return foundUser.get();
		}
	}

	@DeleteMapping("/{date}")
	@ResponseBody
	public User deleteShift(@PathVariable final String id, @PathVariable final String jobName,
			@PathVariable final String dateName, @PathVariable String date) {
		Optional<User> foundUser = usRepo.findById(id);
		User user = foundUser.get();
		Job job = user.getJobs().stream().filter(j -> j.getName().equalsIgnoreCase(jobName)).findAny().get();
		Optional<PayPeriod> maybePeriod = job.getPayPeriods().stream()
				.filter(w -> w.getDateName().equalsIgnoreCase(dateName)).findAny();
		if (maybePeriod.isPresent()) {
			PayPeriod period = maybePeriod.get();
			period.deleteShift(date);
			period.updatePay();
			job.updatePayPeriod(period);
			usRepo.update(user);
			return user;
		} else {
			return foundUser.get();
		}
	}

	@PutMapping("/{oldDate}")
	@ResponseBody
	public User editShift(@PathVariable final String id, @PathVariable final String jobName,
			@PathVariable final String dateName, @RequestBody Shift shift, @PathVariable String oldDate) {
		Optional<User> foundUser = usRepo.findById(id);
		User user = foundUser.get();
		Job job = user.getJobs().stream().filter(j -> j.getName().equalsIgnoreCase(jobName)).findAny().get();
		Optional<PayPeriod> maybePeriod = job.getPayPeriods().stream()
				.filter(p -> p.getDateName().equalsIgnoreCase(dateName)).findAny();
		if (maybePeriod.isPresent()) {
			PayPeriod period = maybePeriod.get();
			shift.calcPay(job.getRate(), user.getTaxRate());
			period.editShift(shift, oldDate);
			period.updatePay();
			job.updatePayPeriod(period);
			usRepo.update(user);
			return user;
		} else {
			return foundUser.get();
		}
	}

}
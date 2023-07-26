package the.best.nettracker.documents.branches;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Job {

	private String name;
	private double rate;
	private List<PayPeriod> periods;

	public Job(String name, double rate, List<PayPeriod> periods) {
		super();
		this.name = name;
		this.rate = rate;
		this.periods = periods;
	}

	public Job(String name, double rate) {
		super();
		this.name = name;
		this.rate = rate;
		periods = new ArrayList<>();
	}

	public Job() {
		periods = new ArrayList<>();
	}

	public double getRate() {
		return rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public List<PayPeriod> getPayPeriods() {
		return periods;
	}

	public void addPayPeriod(PayPeriod period) {
		periods.add(period);
	}

	public void deletePayPeriod(String dateName) {
		List<PayPeriod> updatePeriods = periods.stream().filter(w -> !w.getDateName().equals(dateName))
				.collect(Collectors.toList());
		periods = updatePeriods;
	}

	public void updatePayPeriod(PayPeriod period) {
		List<PayPeriod> updatePeriods = periods.stream().filter(p -> !p.getDateName().equals(period.getDateName()))
				.collect(Collectors.toList());
		updatePeriods.add(period);
		periods = updatePeriods;
	}

	public void updatePayPeriod(PayPeriod period, String oldDateName) {
		PayPeriod oldPeriod = periods.stream().filter(p -> p.getDateName().equals(oldDateName)).findAny().get();
		List<PayPeriod> updatePeriods = periods.stream().filter(p -> !p.getDateName().equals(oldDateName))
				.collect(Collectors.toList());
		oldPeriod.setDateName(period.getDateName());
		updatePeriods.add(oldPeriod);
		periods = updatePeriods;
	}

	public boolean payPeriodExists(PayPeriod period) {
		List<String> periodNames = periods.stream().map(p -> p.getDateName()).collect(Collectors.toList());
		return periodNames.stream().anyMatch(p -> p.equals(period.getDateName()));
	}

	public boolean payPeriodExists(String dateName) {
		List<String> periodNames = periods.stream().map(p -> p.getDateName()).collect(Collectors.toList());
		return periodNames.stream().anyMatch(p -> p.equals(dateName));
	}

	public void setPayPeriods(List<PayPeriod> periods) {
		this.periods = periods;
	}

	@Override
	public String toString() {
		return "Job [name=" + name + ", rate=" + rate + ", periods=" + periods + "]";
	}

}
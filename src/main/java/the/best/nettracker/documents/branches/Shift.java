package the.best.nettracker.documents.branches;

import the.best.nettracker.documents.User;

public class Shift {

	private String date;
	private double hours;
	private double grossPay;
	private double netPay;
	private double taxes;
	private double overtime;

	public Shift(String date, double hoursWorked, double grossPay, double netPay, double taxes, double overtime) {
		super();
		this.date = date;
		this.hours = hoursWorked;
		this.grossPay = grossPay;
		this.netPay = netPay;
		this.taxes = taxes;
		this.overtime = overtime;
	}

	public Shift(User user, Job job, double hoursWorked, String date) {
		super();
		this.grossPay = job.getRate() * hoursWorked;
		this.hours = hoursWorked;
		this.taxes = this.grossPay * user.getTaxRate();
		this.netPay = grossPay - this.taxes;
		this.date = date;
	}

	public Shift(String dateString, double hoursWorked, double overtime) {
		this.date = dateString;
		this.hours = hoursWorked;
		this.overtime = overtime;
	}

	public Shift(String dateString) {
		this.date = dateString;
	}

	public Shift() {

	}

	// this method calculates the pay whenever a shift is posted to shift controller
	public void calcPay(double rate, double taxRate) {
		this.grossPay = Math.round((rate * this.hours) * 100.0) / 100.0;
		this.grossPay += Math.round((this.overtime * (rate * 0.5)) * 100.0) / 100.0;
		this.netPay = Math.round(((this.grossPay * (1 - taxRate)) * 100.0)) / 100.0;
		this.taxes = Math.round((this.grossPay * taxRate) * 100.0) / 100.0;
	}

	public double getHours() {
		return hours;
	}

	public void addHours(double hours) {
		this.hours = hours;
	}

	public double getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(double grossPay) {
		this.grossPay = grossPay;
	}

	public double getNetPay() {
		return netPay;
	}

	public void setNetPay(double netPay) {
		this.netPay = netPay;
	}

	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	public double getOvertime() {
		return overtime;
	}

	public void setOvertime(double overtime) {
		this.overtime = overtime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return "Shift [date=" + date + ", hours=" + hours + ", grossPay=" + grossPay + ", netPay=" + netPay + ", taxes="
				+ taxes + ", overtime=" + overtime + "]";
	}

}

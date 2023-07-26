package the.best.nettracker.documents.branches;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payPeriods")
public class PayPeriod {

	private String dateName; // might have to be a string
	private List<Shift> shifts;
	private double netPay;
	private double grossPay;
	private double taxes;

	public PayPeriod(List<Shift> shifts, double netPay, double grossPay, double taxes) {
		super();
		this.shifts = shifts;
		this.netPay = netPay;
		this.grossPay = grossPay;
		this.taxes = taxes;
	}

	public PayPeriod(String date) {
		this.dateName = date;
		this.shifts = new ArrayList<>();
	}

	public PayPeriod() {
		shifts = new ArrayList<>();
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	public void addShift(Shift shift) {
		shifts.add(shift);
	}

	// removes the old shift and replaces it with the updated version
	public void editShift(Shift shift, String oldDate) {
		List<Shift> updateShifts = shifts.stream().filter(s -> !s.getDate().equals(oldDate))
				.collect(Collectors.toList());
		updateShifts.add(shift);
		shifts = updateShifts;
	}

	// checks for the existence of the shift given the shift
	public boolean shiftNotExist(Shift shift) {
		List<String> dates = shifts.stream().map(s -> s.getDate()).collect(Collectors.toList());
		return dates.stream().noneMatch(str -> str.equals(shift.getDate()));
	}

	// deletes the shift given the date (MM/DD)
	public void deleteShift(String date) {
		List<Shift> updateShifts = shifts.stream().filter(s -> !s.getDate().equals(date)).collect(Collectors.toList());
		shifts = updateShifts;
	}

	public double getNetPay() {
		return netPay;
	}

	public void setNetPay(double netPay) {
		this.netPay = netPay;
	}

	public double getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(double grossPay) {
		this.grossPay = grossPay;
	}

	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	// updates the total pay for the week whenever a new shift is added or a shift
	// is edited
	public void updatePay() {
		this.grossPay = this.shifts.stream().mapToDouble(s -> s.getGrossPay()).sum();
		this.netPay = this.shifts.stream().mapToDouble(s -> s.getNetPay()).sum();
		this.taxes = this.shifts.stream().mapToDouble(s -> s.getTaxes()).sum();
	}

	public String getDateName() {
		return dateName;
	}

	public void setDateName(String dateRange) {
		this.dateName = dateRange;
	}

	@Override
	public String toString() {
		return "PayPeriod [dateName=" + dateName + ", shifts=" + shifts + ", netPay=" + netPay + ", grossPay="
				+ grossPay + ", taxes=" + taxes + "]";
	}

}

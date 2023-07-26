package the.best.nettracker.documents;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import the.best.nettracker.documents.branches.Job;


@Document(collection = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	@Id
	private String id;
	private String name;
	private String userName;
	private String password;
	private double taxRate;
	private List<Job> jobs;

	public User(String name, String username, String password, List<Job> jobs) {
		super();
		this.name = name;
		this.userName = username;
		this.password = password;
		this.jobs = jobs;
	}

	public User(String userName, String password, List<Job> jobs) {
		super();
		this.name = userName;
		this.userName = userName;
		this.password = password;
		this.jobs = jobs;
	}

	public User(String userName, double taxRate, String password) {
		super();
		this.name = userName;
		this.userName = userName;
		this.taxRate = taxRate;
		this.password = password;
	}

	public User(String name) {
		super();
		this.name = name;
		this.userName = name;
		this.jobs = new ArrayList<Job>();
	}

	public User() {
		this.jobs = new ArrayList<Job>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void addJob(Job job) {
		jobs.add(job);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	// this method checks the database for the existence of the job within the user
	// based on job name
	public boolean jobExists(Job job) {
		List<String> jobNames = jobs.stream().map(j -> j.getName()).collect(Collectors.toList());
		return jobNames.stream().anyMatch(name -> name.equalsIgnoreCase(job.getName()));
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", userName=" + userName + ", password=" + password + ", taxRate="
				+ taxRate + ", jobs=" + jobs + "]";
	}

}
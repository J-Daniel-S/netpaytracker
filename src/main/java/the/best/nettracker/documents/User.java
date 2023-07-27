package the.best.nettracker.documents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import the.best.nettracker.documents.branches.Job;


@Document(collection = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

	@Id
	private String id;
	private String name;
	private String userName;
	private String password;
	private double taxRate;
	private List<Job> jobs;
	private String role;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User(String name, String username, String password, List<Job> jobs) {
		super();
		this.name = name;
		this.userName = username;
		this.password = password;
		this.jobs = jobs;
		this.role = "USER";
	}

	public User(String userName, String password, List<Job> jobs) {
		super();
		this.name = userName;
		this.userName = userName;
		this.password = password;
		this.jobs = jobs;
		this.role = "USER";
	}

	public User(String userName, double taxRate, String password) {
		super();
		this.name = userName;
		this.userName = userName;
		this.taxRate = taxRate;
		this.role = "USER";
		this.password = password;
	}

	public User(String name) {
		super();
		this.role = "USER";
		this.name = name;
		this.userName = name;
		this.jobs = new ArrayList<>();
	}

	public User() {
		this.jobs = new ArrayList<>();
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

	@Override
	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	@Override
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

	public String getRole() {
		return role;
	}

}
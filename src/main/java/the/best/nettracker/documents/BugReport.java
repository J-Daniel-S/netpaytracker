package the.best.nettracker.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "bugReports")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BugReport {

	@Id
	private String id;
	private String userId;
	private String text;

	public BugReport(String id, String user, String text) {
		super();
		this.id = id;
		this.userId = user;
		this.text = text;
	}

	public BugReport(String user, String text) {
		super();
		this.userId = user;
		this.text = text;
	}

	public BugReport() {

	}

	public String getId() {
		return id;
	}

	public void setId(String reportId) {
		this.id = reportId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "BugReport [reportId=" + id + ", userId=" + userId + ", text=" + text + "]";
	}

}

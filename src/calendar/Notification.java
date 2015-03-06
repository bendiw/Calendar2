package calendar;

import java.util.List;

public class Notification {
	
	private String subject;
	private String message="";
	
	public String getSubject(){
		return this.subject;
	}
	
	public Notification(List<String> changes) {
		subject = "Meeting changed";
		for (String change : changes) {
			message += change+"\n";
		}
	}
	
	public Notification(Meeting m) {
		subject = "Meeting cancelled";
		message = m.toString();
	}
	
	public Notification(Person p) {
		subject = p.getName()+" declined your invitation.";
	}
	
	public String toString(){
		return subject+"\n"+message;
	}
	
	
	
}

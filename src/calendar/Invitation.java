package calendar;

import java.util.List;

public class Invitation {

	protected Meeting meeting;
	private List<Person> invitedPersons;
	private List<Group> invitedGroups;
	protected Boolean priority;
	protected boolean confirmed;
	
	public Invitation(Meeting meeting, boolean confirmed, List<Person> invitedPersons){
		this.meeting = meeting;
		this.invitedPersons = invitedPersons;
		this.confirmed = confirmed;
	}
	
	private void sendInviteToGroups() {
		for (Group g : invitedGroups) {
			for (Person p : g.members) {
				p.addInvitation(this);
			}
		}
	}
		
	private void sendInviteToPersons() {
		for (Person p : invitedPersons) {
			p.addInvitation(this);				
		}
	}
	
	public void setPriority(boolean pri) {
		if (priority == true) {
			// sjekk om møtet eksisterer i databasen (kan ha blitt slettet uten å fått varsel dersom en
			// person har møtet med priority = false
		}
		this.priority = pri;
	}
	
	public String toString(){
		return "Invited by "+this.meeting.meetingLeader+" to:\n"+this.meeting;
	}
	
	
	public static void main(String[] args) {
		System.out.println("hrei");
	}
		
	
	
}

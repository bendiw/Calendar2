package calendar;

import java.util.List;

public class Invitation {

	protected Meeting meeting;
	private List<Person> invitedPersons;
	private List<Group> invitedGroups;
	
	
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
		
	protected void respond(Person p, boolean ans) { // for at ans==true må ikke møtet krasje i personal calendar
		if (ans == true) {
			meeting.addPerson(p);
			// varsle fra om at p har godtatt 
		}
		else {
			// varsle fra om at p har avslått?
		}
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("hrei");
	}
		
	
	
}

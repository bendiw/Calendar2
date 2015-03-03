package calendar;

import java.util.List;

public class Invitation {

	private Meeting meeting;
	private List<Person> invitedPersons;
	private List<Group> invitedGroups;
	
	private void sendInvite(Group g) {
			
	}
		
	private void sendInvite(Person p) {
			
	}
		
	protected void respond(Person p, boolean ans) {
		if (ans == true) {
			// legg til p i attendinglisten til meeting
		}
		else {
			// varsle fra om at p har avslått?
		}
	}
		
	
	
}

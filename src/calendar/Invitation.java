package calendar;

import java.util.List;

public class Invitation {

	protected Meeting meeting;
	private List<Person> invitedPersons;
	private List<Group> invitedGroups;
	protected Boolean priority;
	
	
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
		this.priority = pri;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("hrei");
	}
		
	
	
}

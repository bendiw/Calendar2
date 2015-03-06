package calendar;

import java.util.List;
import org.joda.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Person implements NotificationListener{
	private GeneralCal cal;
	private String name;
	private String email;
	private int IDno;
	private ArrayList<Group> groups = new ArrayList<Group>();
	protected ArrayList<Invitation> invites = new ArrayList<Invitation>();
	protected ArrayList<Notification> notifications = new ArrayList<Notification>();
	
 
	public Person(String name, int IDno){
		setName(name);
		setIDno(IDno);
	}
	
	public boolean isMemberOf(Group group){
		if(groups.contains(group)){
			return true;
		}
		return false;
	}
	
	public ArrayList<Group> getGroups(){
		return this.groups;
	}

	public GeneralCal getCalendar(){
		return this.cal;
	}
	
	public int getIDno(){
		return this.IDno;
	}
	
	public void setIDno(int id){
		this.IDno=id;
	}
	
	public Person(GeneralCal c){
		this.cal=c;
	}
	
	
	private boolean isValidName(String name){
		int count = 0;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (! (Character.isLetter(c)) && !(c==' ')) {
				return false;
			}
			if(c==' '){
				count++;
			}
		}
		if(count==0){
			return false;
		}
		String[] parts = name.split(" ");
		String firstName = parts[0];
		String surName = parts[1];
		if(firstName.length()<2 || surName.length()<2){
			return false;
		}
		return true;
		}
	
	private boolean isValidEmail(String email){
		int atCount=0;
		if(email=="\0"){
			return true;
		}
		for (int i = 0; i < email.length(); i++) {
			char c = email.charAt(i);
			if(c=='@'){
				atCount++;
			}
		}
		if(atCount==0||atCount>1){
			return false;
		}
		return true;
	}

	public void setName(String name) {
		if (isValidName(name)){
			this.name = name;
		}else{
			throw new IllegalArgumentException("Invalid name!");
		}
	}
		
	public String getName(){
		return this.name;
	}
	
	public String getEmail(){
		return this.email;
	}

	
	public void createMeeting(Meeting m){
		cal.addMeeting(m);
	}

	public void newNotification(Notification n) {
		return;
		// TODO Auto-generated method stub
		
	}
	
	public void addInvitation(Invitation inv) {
		invites.add(inv);
		// trenger kanskje mer validering!

	}
	
	public ArrayList<Notification> getNotifications() {
		return notifications;
	}
	
	
	
//	public void respond(Invitation inv, boolean ans, boolean pri) {
//		if (ans == false) {
//			// varsle møteleder om at this har avslått invitasjonen
//			invites.remove(inv);
//		}else {
//			invites.remove(inv);
//			cal.addMeeting(inv.meeting);
//			
//			if (cal.collidesWith(inv.meeting).isEmpty()) {
//				inv.meeting.setPriority(true); //setter møtet til prioritet 1
//				inv.meeting.addPerson(this);
//				// varsle møteleder i meeting om at person har godtatt invitasjonen
//			}else {
//				if (pri == true) {
//					for (Meeting m : cal.collidesWith(inv.meeting)) {
//						m.setPriority(false);
//						m.removePerson(this); //fjerner person fra attendinglist til møtene som krasjer, hvis person er der.
//					}inv.meeting.setPriority(true);
//					inv.meeting.addPerson(this);
//					// varsle møteleder i meeting om at person har godtatt invitasjonen
//				}else {
//					inv.meeting.setPriority(false);
//				}
//			}
//		}
//	}
	
	
	
	
}

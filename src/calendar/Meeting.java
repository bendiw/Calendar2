package calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.*;

public class Meeting extends Database {
	
	private int meetingID;
	private LocalDate date;
	private String description;
	private String title;
	protected Person meetingLeader;
	private List<Person> attending; //listeners for notifications
	private List<Person> maybeAttending;
	private List<Group> groups; // Mulig å fjerne denne? Bare persons som kan attende.
	private String startTime;
	private String endTime="-1";
	private String room;
	protected boolean priority;
	
	public int[] getDuration(){
		int hours = Integer.parseInt(endTime.substring(0, 2))-Integer.parseInt(startTime.substring(0, 2));
		int mins = Integer.parseInt(endTime.substring(2))-Integer.parseInt(startTime.substring(2));
		if(mins<0){
			return new int[] {hours-1, mins+60};
		}
		return new int[] {hours, mins};
	}
	
	public String toString(){
		return title+"\n"+durationToString()+"\n"+"Description: "+description+"\nAttending: ";
	}
	
	public String toStringSimple(){
		return title+"\n"+durationToString()+"\n"+"Description: "+description;
	}
	
	public String durationToString(){
		int[] duration = getDuration();
		if(duration[1]==0){
			return (this.startTime+" - "+endTime+"\n"+"Duration: "+duration[0]+"h\n");
		}else if(duration[0]==0){
			return (this.startTime+" - "+endTime+"\n"+"Duration: "+duration[1]+"min\n");
		}
		return (this.startTime+" - "+endTime+"\n"+"Duration: "+duration[0]+"h"+duration[1]+"min");
	}
	
	public void addPerson(Person p){
		if(!attending.contains(p)){
			attending.add(p);
		}
	}
	
	public void removePerson(Person p) {
		if (attending.contains(p)) {
			attending.remove(p);
		}
	}
	
	public void addPersonToMaybe(Person p) {
		removePerson(p);
		if (!maybeAttending.contains(p)) {
			maybeAttending.add(p);
		}
	}
	
	public void removePersonFromMaybe(Person p) {
		if (maybeAttending.contains(p)) {
			maybeAttending.remove(p);
		}
	}
	
	public void cancelMeeting() {
		Notification n = new Notification(this);
		for (Person p : attending) {
			p.notifications.add(n);
			p.getCalendar().deleteMeeting(this);
			//dersom møtet har pri1 må et annet settes til pri1, (ikke ferdig)
			//sletter bare møtet for de som står på deltar, ikke på kanskje, case? (ikke ferdig)
			for (Invitation inv : p.oldInvites) {
				if (inv.meeting == this) {
					p.oldInvites.remove(inv); // fjerner invitasjonen til møtet for alle persons som attender
					if (inv.priority == true) {
						//kjør en metode som får bruker til å velge mellom møtene som kolliderte med inv
					}
				}
			}
		}
		meetingLeader.notifications.add(n);
		meetingLeader.getCalendar().deleteMeeting(this);
		
		
		//må slette møtet i databasen (ikke ferdig)

	}
	
	public void setPriority(boolean pri) {
		priority = pri;
	}
	
	public boolean getPriority() {
		return priority;
	}
	
	public void addGroup(Group g){
		if(!groups.contains(g)){
			groups.add(g);
		}
	}
	
	public void cloneFields(Meeting toClone){
		this.date=toClone.getDate();
		this.title=toClone.getTitle();
		this.description=toClone.getDescription();
		this.startTime=toClone.getStartTime();
		this.endTime=toClone.getEndTime();
	}
	
	public boolean collides(Meeting m){
		int otherStart=Integer.parseInt(m.getStartTime());
		int otherEnd = Integer.parseInt(m.getEndTime());
		int thisStart = Integer.parseInt(startTime);
		int thisEnd = Integer.parseInt(endTime);
		if(otherEnd > thisStart && otherEnd < thisEnd){
			return true;
		}else if(otherStart>thisStart&&otherStart<thisEnd){
			return true;
		}else if(otherStart>thisStart&&otherEnd<thisEnd){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public Meeting(int id,LocalDate date, Person leader, String start, String end, String title){
		this.meetingID=id;
		this.date=date;
		this.meetingLeader = leader;
		this.startTime=start;
		this.endTime=end;
		this.title=title;
	}
	
	public Meeting(LocalDate date, Person leader){
		this.date=date;
		this.meetingLeader=leader;
	}
	
	public Meeting(LocalDate date){
		this.date=date;
	}
	
	
	public LocalDate getDate(){
		return this.date;
	}
	
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		if(title.isEmpty()){
			this.title="Untitled Meeting";
			return;
		}
		this.title = title;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		int end = Integer.parseInt(this.endTime);
		int start = Integer.parseInt(startTime);
		if(end<0){
			end=start+1;
		}
		if (startTime.matches("[0-9]+") && startTime.length() > 3&& (end-start)>0) {
			this.startTime = startTime;
			// 
		}else{
			throw new IllegalArgumentException("Invalid format!");
		}
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		int end = Integer.parseInt(endTime);
		int start = Integer.parseInt(startTime);
		if (endTime.matches("[0-9]+") && endTime.length()>3&&(end-start)>0) {
			this.endTime=endTime;
		}else{
			throw new IllegalArgumentException("End time cannot be before start time!");
		}
	}


	public String getRoom() {
		return room;
	}


	public void setRoom(String room) {
		this.room = room;
	}

	
	public void fireNotification(Notification n){
//		if(n.getSubject().contains("declined")){
//			meetingLeader.notifications.add(n);
//			return;
////		}else{
//			for (Person p : attending) {
//				p.notifications.add(n);
//			}
//		}
		meetingLeader.notifications.add(n);
	}
	
	public List<String> getChanges(Meeting m) {
		List<String> changes = new ArrayList<String>();
		if (!this.date.equals(m.date)) {
			changes.add("The meeting date is changed from "+m.date+" to "+this.date+"\n");
		if (!this.description.equals(m.description)) {
			changes.add("The meeting description is changed to:\n"+this.description+"\n");
		}if (!this.title.equals(m.title)) {
			changes.add("The meeting title is changed from '"+m.title+"' to '"+this.title+"'\n");
		}if (!this.startTime.equals(m.startTime) || (!this.endTime.equals(m.endTime))) {
			changes.add("The duration of the meeting is changed to: "+this.durationToString()+"\n");
//		}if (!this.room.equals(m.room)) {
//			changes.add("The meeting room is changed from "+m.room+" to "+this.room+"\n\n");
		}
		}
		return changes;
	}
	
	


	public static void main(String[] args) {
		
	}

}

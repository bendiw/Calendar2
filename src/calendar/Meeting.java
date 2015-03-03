package calendar;

import java.util.Date;
import java.util.List;

import org.joda.time.*;

public class Meeting {
	
	private LocalDate date;
	private String description;
	private String title;
	private Person meetingLeader;
	private List<Person> attending;
	private List<Group> groups;
	private String startTime;
	private String endTime;
	private String room;
	
	public int[] getDuration(){
		int hours = Integer.parseInt(endTime.substring(0, 2))-Integer.parseInt(startTime.substring(0, 2));
		int mins = Integer.parseInt(endTime.substring(2))-Integer.parseInt(startTime.substring(2));
		if(mins<0){
			return new int[] {hours-1, mins+60};
		}
		return new int[] {hours, mins};
	}
	
	public String toString(){
		int[] duration = getDuration();
		if(duration[1]==0){
			return (title+"\n"+this.startTime+" - "+endTime+"\n"+"Duration: "+duration[0]+"h\n"+"Description: "+description+"\nAttending: ");
		}else if(duration[0]==0){
			return (title+"\n"+this.startTime+" - "+endTime+"\n"+"Duration: "+duration[1]+"min\n"+"Description: "+description+"\nAtteding: ");

		}
		return (title+"\n"+this.startTime+" - "+endTime+"\n"+"Duration: "+duration[0]+"h"+duration[1]+"min\n"+"Description: "+description+"\nAttending:");
	}
	
	public void addPerson(Person p){
		if(!attending.contains(p)){
			attending.add(p);
		}
	}
	
	public void addGroup(Group g){
		if(!groups.contains(g)){
			groups.add(g);
		}
	}
	
	
	
	public Meeting(LocalDate date, Person leader, String start, String end, String title){
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
		this.title = title;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		if (startTime.matches("[0-9]+") && startTime.length() > 3) {
			this.startTime = startTime;
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
		
	}
	
	


	public static void main(String[] args) {
		
	}

}

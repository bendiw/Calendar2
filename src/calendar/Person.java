package calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.joda.time.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kalender.GeneralCal;
import kalender.Group;
import kalender.Invitation;
import kalender.Meeting;
import kalender.Notification;

public class Person extends Database implements NotificationListener{
	private GeneralCal cal;
	private String name;
	private String email;
	private String address;
	private int mobile;
	private String position;
	private int postnr;

	private int userID;
	private ArrayList<Group> groups = new ArrayList<Group>();
	protected ArrayList<Invitation> invites = new ArrayList<Invitation>();
	protected ArrayList<Notification> notifications = new ArrayList<Notification>();
	protected ArrayList<Invitation> oldInvites = new ArrayList<Invitation>();

	
	
	public List<Invitation> getInv(){
		return this.invites;
	}
	
	public List<Invitation> getOldInv(){
		return this.oldInvites;
	}
	
	//fra User
	String password;
	boolean newUser;
	
	Statement stmt = null;
	ResultSet rs = null;
	String query = null;
	PreparedStatement pstmt = null;
	
 
	public Person(int userID, String firstname, String lastname, String address, int mobile, String email, String position, int postnr){
//		setName(name);
		setName(firstname+" "+lastname);
		setUserID(userID);
		setEmail(email);
		this.address=address;
		this.position=position;
		this.mobile=mobile;
		this.postnr=postnr;
		
	}
	
	
	public Person(String email, String password, boolean newUser) throws Exception {
		this.email = email;
		this.password = password;
		this.newUser = newUser;
		if(newUser) {
			if(emailExists(email)) {
				System.out.println("Eposten finnes allerede, pr�v en annen epost.");
				throw new IllegalArgumentException();
			} else {
				makeNewUser(email, password);
			}
			
		} else {
			if(emailExists(email)) {
				loginUser(email, password);
			} else {
				System.out.println("Eposten finnes ikke, pr�v en ny epost.");
				throw new IllegalArgumentException();
			}
		}
	}
	
	public boolean makeNewUser(String email, String password) throws Exception {
		if(isValidEmail(email)) {
			try {
				openConnection();
				pstmt = conn.prepareStatement("INSERT INTO Bruker (epostadresse, passord) "
						+ " VALUES (?, ?)");
				pstmt.setString(1, email);
				pstmt.setString(2, password);
				
				pstmt.execute();
				return true;
			} finally {
				closeConnection();
			}	
		} else {
			System.out.println("Ugyldig email");
//			return false;
			throw new IllegalArgumentException();
		}
	}
	
	public void loginUser(String email, String password) throws Exception {
			try {
				openConnection();
				pstmt = conn.prepareStatement("SELECT * FROM Bruker B WHERE B.epostadresse = '" + email + "';");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					this.password = rs.getString("passord");
				}
				
				if (this.password.equals(password)) {
					System.out.println("Login successfull");
				} else {
					System.out.println("Login unsuccessfull, wrong password");
					throw new IllegalArgumentException();
				}
				
			} finally {
				closeConnection();
			}	
	}
	

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}
	
	public boolean emailExists(String email) throws Exception {
		try {
			openConnection();
			pstmt = conn.prepareStatement("SELECT B.epostadresse FROM Bruker B WHERE B.epostadresse = '" + email + "';");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return true;
			}
		} finally {
			closeConnection();
		}
		return false;
	}
	
	public String toString(){
		return this.userID+"\n"+name+"\n"+email+"\n"+address+"\n"+mobile+"\n"+position+"\n"+postnr;
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
	
	public int getUserID(){
		return this.userID;
	}
	
	public void setUserID(int userID){
		this.userID=userID;
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
	
	public void setEmail(String email) {
//		if(isValidEmail(email)) {
			this.email = email;
////		} else {
//			throw new IllegalArgumentException("Invalid email");
//		}
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

	public void respond(Invitation inv, boolean ans, boolean pri) {
		if (ans == false) {
			Notification noti = new Notification(this);
			inv.meeting.meetingLeader.notifications.add(noti);
			invites.remove(inv);
		}else {
			invites.remove(inv);
			oldInvites.add(inv);
			cal.addMeeting(inv.meeting);
			
			if (cal.collidesWith(inv.meeting).isEmpty()) {
				inv.setPriority(true); //setter møtet til prioritet 1
				inv.meeting.addPerson(this);
			}else {
				if (pri == true) {
					for (Invitation i : oldInvites) {
						if(cal.collidesWith(inv.meeting).contains(i.meeting)){
							i.setPriority(false);
							i.meeting.removePerson(this);
						}
					}inv.setPriority(true);
					inv.meeting.addPerson(this);
				}else {
					inv.setPriority(false);
				}
			}
		}
	}
	
	
}

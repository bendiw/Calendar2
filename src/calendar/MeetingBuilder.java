package src.calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import org.joda.time.LocalDate;

public class MeetingBuilder extends Database{
		
		private int meetingID;
		private String description;
		private String place;
		private String startTime;
		private String endTime;
		private int room;
		private LocalDate date;
		private Person meetingLeader;
		private String title;
		//database
		private Statement stmt = null;
		private ResultSet rs;
		private String query = null;
		private PreparedStatement pstmt = null;
		private int ownerID;
		
		ArrayList<Integer> list=new ArrayList<Integer>();
		PersonBuilder pb = new PersonBuilder();
		
		public MeetingBuilder(int ownerID){
			this.ownerID=ownerID;
		}
		
		public ArrayList<Integer> addAttendingUserIDs(int meetingID) throws Exception {
			super.openConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT B.BrukerID FROM Bruker B, Invitasjon I, M�te M WHERE M.m�teID = I.M�te_m�teID AND I.Bruker_brukerID = B.BrukerID "
					+ "AND I.Bekreftet = 1 AND I.prioritet = 1 AND M.m�teID = " + meetingID + ";"); 
			while(rs.next()) {
				int toAdd = rs.getInt("brukerID");
				list.add(toAdd);
			}
			return list;
		}
		
		public List<Person> getAllAttending(int meetingID) throws Exception{
			List<Person> toReturn = new ArrayList<Person>();
			ArrayList<Integer> list = addAttendingUserIDs(meetingID);
			for (int userID : list) {
				toReturn.add(pb.getPerson(userID));
			}
			return toReturn;
		}
		
		
			
		public boolean meetingIDExists(int meetingID) throws Exception {
			try {
				openConnection();
				pstmt = conn.prepareStatement("SELECT M.m�teID FROM M�te M WHERE M.m�teID = " + meetingID + ";");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					return true;
				}
			} finally {
				closeConnection();
			}
			return false;
		}
		
		private String convertLocalDate(LocalDate date){
			return date +"";
		}
		
		
		
		public ArrayList<Integer> addMeetingIDtoList() throws Exception {
			ArrayList<Integer> list = new ArrayList<Integer>();
				super.openConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT m�teID FROM M�te, Invitasjon WHERE M�te.m�teID = Invitasjon.M�te_m�teID AND Invitasjon.Bruker_brukerID = "+ this.ownerID + ";");
				while(rs.next()) {
					list.add(rs.getInt("m�teID"));
				}
				closeConnection();
			return list;
		}
		
		public List<Meeting> getAllMeetings() throws Exception{
			List<Meeting> toReturn = new ArrayList<Meeting>();
			ArrayList<Integer> list = addMeetingIDtoList();
			for (int meetingID : list) {
				toReturn.add(getMeeting(meetingID));
			}
			return toReturn;
		}
		
		public Meeting getMeeting(int meetingID) throws Exception{
			Meeting meeting=null;
			if (meetingIDExists(meetingID)) {
				try {
					openConnection();
					pstmt = conn.prepareStatement("SELECT * FROM M�te WHERE m�teID = " + meetingID + ";");
					rs = pstmt.executeQuery();
					while (rs.next()) {
						this.meetingID = rs.getInt("m�teID");
						this.description = rs.getString("beskrivelse");
						this.room = rs.getInt("romID");
						this.place = rs.getString("sted");
						this.title = rs.getString("tittel");
						String[] startDate = rs.getString("dato").split("-");
						this.date = new LocalDate(Integer.parseInt(startDate[0]),Integer.parseInt(startDate[1]),Integer.parseInt(startDate[2]));
						String endTime = rs.getString("tilTidspunkt").replace(":","").substring(0,4);
						String fromTime = rs.getString("fraTidspunkt").replace(":", "").substring(0,4);
						this.endTime = endTime;
						this.startTime=fromTime;
					}
					meeting = new Meeting(date, meetingLeader, startTime, endTime, title);
					if(description !=null){
						meeting.setDescription(description);
					}
					meeting.setRoom(room+"");
					} finally {
						closeConnection();
					}
			} else {
				System.out.println("M�teID " + meetingID + " finnes ikke i databasen");
			}
			return meeting;
		}
		
		public static void main(String[] args) throws Exception{
			Person p = new Person("larshbj@gmail.com", "kalender", false);
			p.setUserID(8);
			MeetingBuilder mb = new MeetingBuilder(p.getUserID());
			System.out.println(mb.getMeeting(1));
		}
}

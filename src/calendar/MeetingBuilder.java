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
		
		private ArrayList<Integer> list = new ArrayList<Integer>();
			
		public boolean meetingIDExists(int meetingID) throws Exception {
			try {
				openConnection();
				pstmt = conn.prepareStatement("SELECT M.møteID FROM Møte M WHERE M.møteID = " + meetingID + ";");
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
		
		
		
		public ArrayList<Integer> addMeetingIDtoList(LocalDate date) throws SQLException {
			String dateInput = convertLocalDate(date);
			stmt = conn.createStatement();
			stmt.executeQuery("SELECT møteID FROM Møte WHERE Møte.dato = "+ dateInput+ ";");
			while(rs.next()) {
				list.add(rs.getInt("møteID"));
			}
			return list;
		}
		
		public List<Meeting> getAllMeetings(LocalDate date) throws Exception{
			List<Meeting> toReturn = new ArrayList<Meeting>();
			ArrayList<Integer> list = addMeetingIDtoList(date);
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
					pstmt = conn.prepareStatement("SELECT * FROM Møte WHERE møteID = " + meetingID + ";");
					rs = pstmt.executeQuery();
					while (rs.next()) {
						this.meetingID = rs.getInt("møteID");
						this.description = rs.getString("beskrivelse");
						this.room = rs.getInt("romID");
						this.place = rs.getString("sted");
						this.title = rs.getString("tittel");
						String[] startDateTime = rs.getString("fraTidspunkt").split(" ");
						String[] startDate = startDateTime[0].split("-");
						this.date = new LocalDate(Integer.parseInt(startDate[0]),Integer.parseInt(startDate[1]),Integer.parseInt(startDate[2]));
						String[] startTime2 = startDateTime[1].split(":");
						this.startTime = startTime2[0] + startTime2[1];
						String[] endDateTime = rs.getString("tilTidspunkt").split(" ");
						String[] endTime2 = endDateTime[1].split(":");
						this.endTime = endTime2[0] + endTime2[1];
					}
					meeting = new Meeting(date, meetingLeader, startTime, endTime, title);
					if(description !=null){
						meeting.setDescription(description);
					}
					meeting.setRoom(room);
					} finally {
						closeConnection();
					}
			} else {
				System.out.println("MøteID " + meetingID + " finnes ikke i databasen");
			}
			return meeting;
		}
		
		public static void main(String[] args) throws Exception{
			MeetingBuilder mb = new MeetingBuilder();
			mb.getMeeting(1);
		}
}

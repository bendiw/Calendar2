package src.calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvitationBuilder extends Database {
	
	protected MeetingBuilder mb;
	protected PersonBuilder pb;
	//database
	protected Statement stmt = null;
	protected ResultSet rs = null;
	protected String query = null;
	protected PreparedStatement pstmt = null;
	
	ArrayList<Integer> list;
		
	public boolean invitationIDExists(int meetingID, int brukerID) throws Exception {
		try {
			openConnection();
			pstmt = conn.prepareStatement("SELECT I.invitationID, brukerID FROM invitasjon I WHERE I.bruker_brukerID = " + brukerID + "AND I.møte_møteID = " + meetingID + ";" );
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return true;
			}
		} finally {
			closeConnection();
		}
		return false;
	}
	
	public ArrayList<Integer> addMeetingIDtoList(int userID) throws SQLException {
		stmt = conn.createStatement();
		stmt.executeQuery("SELECT møte_møteID FROM invitasjon WHERE invitasjon.bruker_brukerID = "+ userID + ";");
		while(rs.next()) {
			list.add(rs.getInt("møte_møteID"));
		}
		return list;
	}
	
	
	public List<Invitation> getAllInvitations(int userID) throws SQLException{
		List<Invitation> toReturn = new ArrayList<Invitation>();
		ArrayList<Integer> list = addMeetingIDtoList(userID);
		for (int meetingID : list) {
			toReturn.add(getInvitation(meetingID, userID));
		}
		return toReturn;
	}
	
//	public List<Group> getInvitedGroups(int meetingID){
//		ArrayList<Group> invGroups = new ArrayList<Group>();
//		
//		
//	}
	
	public List<Person> getInvitedPersons(int meetingID) throws Exception{
		ArrayList<Person> invited = new ArrayList<Person>();
			try {
				openConnection();
				pstmt = conn.prepareStatement("SELECT bruker_brukerID FROM invitasjon WHERE I.bekreftet = " + null + ";");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					invited.add(pb.getPerson(rs.getInt("bruker_brukerID")));
//					this.position = rs.getString("stilling");
				}
				} finally {
					closeConnection();
				}
			return invited;
	}
	
	public Invitation getInvitation(int meetingID, int brukerID){
		Invitation inv;
		boolean confirmed;
		Meeting meeting = mb.getMeeting(meetingID);
		List<Person> invited = getInvitedPersons(meetingID);
		if (invitationIDExists(meetingID, brukerID)) {
			try {
				openConnection();
				pstmt = conn.prepareStatement("SELECT * FROM invitasjon WHERE I.møte_møteID = " + meetingID + "AND I.bruker_brukerID = " +brukerID+ ";");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					confirmed = rs.getBoolean("bekreftet");
//					this.position = rs.getString("stilling");
				}
				inv = new Invitation(meeting, confirmed, invited);
				} finally {
					closeConnection();
				}
			return inv;
		} else {
			System.out.println("Person "+brukerID + " is not invited to this meeting.");
		}
	}


	

}

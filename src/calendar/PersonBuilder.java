package src.calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PersonBuilder extends Database {

	//integers
		int userID;
		int postnr;
		int mobile;
		//strings
		String password;
		String firstname;
		String lastname;
		String address;
		String email;
		String position;
		//booleans
		boolean newUser;
		//database
		Statement stmt = null;
		ResultSet rs;
		String query = null;
		PreparedStatement pstmt = null;
		
		
		ArrayList<Integer> list=new ArrayList<Integer>();
			
		public boolean userIDExists(int userID) throws Exception {
			try {
				openConnection();
				pstmt = conn.prepareStatement("SELECT B.brukerID FROM Bruker B WHERE B.brukerID = " + userID + ";");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					return true;
				}
			} finally {
				closeConnection();
			}
			return false;
		}
		
		public ArrayList<Integer> addUserIDtoList() throws Exception {
			super.openConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT brukerID FROM Bruker;");
			while(rs.next()) {
				int toAdd = rs.getInt("brukerID");
				list.add(toAdd);
			}
			return list;
		}
		
		public List<Person> getAllPersons() throws Exception{
			List<Person> toReturn = new ArrayList<Person>();
			ArrayList<Integer> list = addUserIDtoList();
			for (int userID : list) {
				toReturn.add(getPerson(userID));
			}
			return toReturn;
		}
		
		public Person getPerson(int userID) throws Exception{
			Person person = null;
			if (userIDExists(userID)) {
				try {
					openConnection();
					pstmt = conn.prepareStatement("SELECT * FROM Bruker WHERE brukerID = " + userID + ";");
					rs = pstmt.executeQuery();
					while (rs.next()) {
						this.userID = rs.getInt("brukerID");
						this.firstname = rs.getString("fornavn");
						this.lastname = rs.getString("etternavn");
						this.address = rs.getString("adresse");
						this.mobile = rs.getInt("mobilnr");
						this.email = rs.getString("epostadresse");
						this.position = rs.getString("stilling");
						this.postnr = rs.getInt("postnr");
					}
					person = new Person(userID, firstname, lastname, address, mobile, email, position, postnr);
					} finally {
						closeConnection();
					}
			} else {
				System.out.println("BrukerID " + userID + " finnes ikke i databasen");
			}
			return person;
		}
		
		public ArrayList<Integer> addUserIDtoGroupList(int groupID) throws Exception {
			super.openConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT B.brukerID FROM Bruker B, Gruppe_has_Bruker GB WHERE GB.Bruker_brukerID = B.brukerID "
					+ "AND GB.Gruppe_gruppeID = " + groupID + ";");
			while(rs.next()) {
				list.add(rs.getInt("B.brukerID"));
			}
			return list;
		}
		
		public List<Person> getPersonByGroup(int groupID) throws Exception {
			List<Person> toReturn = new ArrayList<Person>();
			ArrayList<Integer> list = addUserIDtoGroupList(groupID);
			for (int userID : list) {
				toReturn.add(getPerson(userID));
			}
			return toReturn;
		}
		
		public static void main(String[] args)throws Exception {
			PersonBuilder pb = new PersonBuilder();
//			for (Person p : pb.getAllPersons()) {
//				System.out.println(p);
			for (Person p : pb.getPersonByGroup(1)) {
				System.out.println(p);
			}
				
			}
		}
		
//		public String toString(){
//			List<Person> personList = new ArrayList<Person>();
//			String s = null;
//			try {
//				personList = getAllPersons();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			for (Person person : personList) {
//				s = person.getEmail() + "\n";
//			}
//			return s;
//		}
//}}

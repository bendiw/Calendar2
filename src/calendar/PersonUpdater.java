package src.calendar;

import java.sql.SQLException;
import java.sql.Statement;


public class PersonUpdater extends Database {

	String email;
	Statement stmt;
	Person p;
	
	public void updateAll(String firstname, String lastname, String address, int postnr, int mobile, String position, String email) throws Exception {
		try {
			openConnection();
			stmt = conn.createStatement();
			String updateAll = "UPDATE Bruker SET fornavn = '" + firstname + "', etternavn = '" + lastname + "', adresse = '" + address +
					"', postnr = " + postnr + ", mobilnr = " + mobile + ", stilling = '" + position + "' WHERE epostadresse = '" + email + "';";
			stmt.executeUpdate(updateAll);
			
			
		} finally {
			closeConnection();
		}
	}
	
	public void updateEmail(String email) throws SQLException {
		stmt = conn.createStatement();
		String updateEmail = "UPDATE Bruker SET epostadresse = '" + email + "' WHERE brukerID = " + p.getUserID() + ";";
		stmt.executeUpdate(updateEmail);
	}
	
//	public void updatePassword(String password) throws SQLException {
//		stmt = conn.createStatement();
//		String sql1 = "UPDATE bruker SET passord = " + password + "WHERE brukerID = " + p.getPassword() + ";";
//		stmt.executeUpdate(sql1);
//	}
	
//	public void run() throws Exception {
//		PersonBuilder pb = new PersonBuilder();
//		openConnection();
//		Person p = pb.getPerson(15);
//		updateAll("Lars", "Berg-Jensen", "Klæbuveien 124", 7014, 11111111, "vaskehjelp", "larshbj@gmail.com");
//		
//		
//		
//		
//	}
//	
//	public static void main(String[] args) throws Exception {
//		PersonUpdater pu = new PersonUpdater();
//		pu.run();
//	}
	
	
	
}

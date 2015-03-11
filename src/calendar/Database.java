package src.calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

	public Connection conn = null;
	  private Statement stmt = null;
	  private ResultSet rs = null;

	  public void openConnection() throws Exception {
	      try {
			// This will load the MySQL driver, each DB has its own driver
			  Class.forName("com.mysql.jdbc.Driver");
			  // Setup the connection with the DB
			  conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/all_s_gruppe44_kalender", "larshbe", "gruppe44");
//	      	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "lars", "kalender");
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	  
	  public void closeConnection() throws Exception {
		  if (stmt != null) { 
			 stmt.close(); 
		  }
		  
		  if (rs != null) {
			  rs.close();
		  }
		  
		  if (conn != null) {
			  conn.close();
		  }
	  }
	
}

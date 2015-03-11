package calendar;


public class StringTest {

	import java.sql.*;
	//import java.util.Scanner;


	public class DbConnector {

		private Connection con;
		private Statement st;
		private ResultSet rs;


		public DbConnector() {
			try{
				Class.forName("com.mysql.jdbc.Driver");

				con = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/all_s_gruppe44_kalender","hshansen","gruppe44");
				st = con.createStatement();


			}catch (Exception e){
				System.out.println("Error: "+e);
			}
		}

		public void getData() {
			try{

				/*String query = "SELECT B.brukerID FROM Bruker B";
				rs = st.executeQuery(query);
				System.out.println("Records from database: \n");
				while(rs.next()) {
					String bruker = rs.getString("brukerID");
					System.out.println("Bruker: "+bruker);			
				} 


				Scanner sc = new Scanner(System.in);
				System.out.println("Vennligst fyll ut følgende: \n");
				System.out.println("BrukerID: ");
				int brukerID = sc.nextInt();
				System.out.println("Passord: ");
				String passord = sc.next();
				System.out.println("Fornavn: ");
				String fornavn = sc.next();
				System.out.println("Etternavn: ");
				String etternavn = sc.next();
				System.out.println("Adresse: ");
				String adresse = sc.next();
				System.out.println("Postnummer: ");
				int postnr = sc.nextInt();
				System.out.println("mobilnr: ");
				int mobilnr = sc.nextInt();
				System.out.println("Epostadresse: ");
				String epostadresse = sc.next();
				System.out.println("Stilling: ");
				String stilling = sc.next();
				 */

				/*
				int brukerID = 84;
				String passord = "gruppe44";
				String fornavn = "Mons";
				String etternavn = "Larsen";
				String adresse = "Skogen 1";
				int postnr = 7014;
				int mobilnr = 45637285;
				String epostadresse = "lars@skogen.no";
				String stilling = "Bjørn";
				 */

				String sql = "INSERT INTO `Bruker`(`brukerID`, `passord`, `fornavn`, `etternavn`, `adresse`, `postnr`, `mobilnr`, `epostadresse`, `stilling`) VALUES (89, 'Test', 'Mons', 'Larsen', 'Skogen 1', 7014, 40987635, 'monsen@larsen.no', 'Sjef')";
				st.executeUpdate(sql);


				rs.close();
				con.close();
				st.close();
				//sc.close();

			}catch(Exception e) {
				System.out.println("Error= "+e);
			}
		}


		public static void main(String[] args) {
			DbConnector connect = new DbConnector();
			connect.getData();

		}
	}

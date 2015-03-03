package calendar;

import java.io.ObjectInputStream.GetField;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.joda.time.LocalDate;

public class CalendarPrinter {
	
	private String header = "mo\ttu\twe\tth\tfr\tsa\tsu";
	
	public String getMonthName(int month){
		switch (month) {
		case 0: return "January";
		case 1: return "February";
		case 2: return "March";
		case 3: return "April";
		case 4: return "May";
		case 5: return "June";
		case 6: return "July";
		case 7: return "August";
		case 8: return "September";
		case 9: return "October";
		case 10: return "November";
		case 11: return "December";
		default: return null;
		}
	}
	
	public void print(GeneralCal g){
		System.out.println("\t\t"+getMonthName(g.getMonth())+" - "+g.getYear());
		System.out.println(header);
		int day = g.getFirstDay()-1;
		int date=1;
		if(day ==0){
			day=7;
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				String star="";
				if(j<day-1&&i==0){
					System.out.print("\t");
				}else if(date<=g.getLastDate()){
					if(!g.isDateClear(new LocalDate(g.getYear(),g.getMonth()+1,date))){
						star = "*";
					}
				System.out.print(date+star+"\t");
				date++;
				}else{
					System.out.print("\t");
				}
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args) {
		Group g = new Group("Markedsføring");
		GroupCal gc = new GroupCal(g);
		CalendarPrinter cp = new CalendarPrinter();
		gc.addMeeting(new Meeting(new LocalDate()));
		LocalDate d1 = new LocalDate();
		LocalDate d2 = new LocalDate(2015,2,26);
		gc.addMeeting(new Meeting(d2));	
		cp.print(gc);
		for (int i = 0; i < 6; i++) {	
			gc.rollMonth(true);
			cp.print(gc);
		}
		
//		System.out.println(cp.cal.get(Calendar.DAY_OF_WEEK));
//		System.out.println(cp.cal.get(Calendar.MONDAY)+" "+cp.cal.get(Calendar.TUESDAY)+" "+cp.cal.get(Calendar.WEDNESDAY)+" "+cp.cal.get(Calendar.THURSDAY));
	}
	

}

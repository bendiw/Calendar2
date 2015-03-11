package calendar;

import org.joda.time.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralCal {
	
	private Map<LocalDate,List<Meeting>> meetingsByDate;
	private Calendar cal;
	
	public void mapMeetings(List<Meeting> meetings){
		for (Meeting meeting : meetings) {
			if(meetingsByDate.containsKey(meeting.getDate())){
				List<Meeting> toAdd = meetingsByDate.get(meeting.getDate());
				toAdd.add(meeting);
				meetingsByDate.put(meeting.getDate(), toAdd);							
			}else{
				List<Meeting> toAdd = new ArrayList<Meeting>();
				toAdd.add(meeting);
				meetingsByDate.put(meeting.getDate(), toAdd);
			}
		}
	}
	
	public void RollYear(boolean forward){
		if(forward){
			cal.add(Calendar.YEAR, 1);
			return;
		}
		cal.add(Calendar.YEAR,-1);
	}
	
	
	public List<Meeting> collidesWith(Meeting m){
		List<Meeting> meetingsOnDate = meetingsByDate.get(m.getDate());
		List<Meeting> toReturn = new ArrayList<Meeting>();
		for (Meeting meeting : meetingsOnDate) {
			if(meeting.collides(m)){
				toReturn.add(meeting);
			}
		}
		return toReturn;
	}
	

	
	public List<Meeting> getDayAgenda(LocalDate date){
		if(meetingsByDate.containsKey(date)){
			return meetingsByDate.get(date);
		}
		return new ArrayList<Meeting>();
	}
	
	public void rollMonth(boolean forward){
		if(forward){
			cal.add(Calendar.MONTH, 1);
			return;
		}
		cal.add(Calendar.MONTH,-1);
	}
	
	public GeneralCal(){
		meetingsByDate = new HashMap<LocalDate, List<Meeting>>();
		cal = GregorianCalendar.getInstance();
	}
	
	public Calendar getCal(){
		return this.cal;
	}
	
	public int getYear(){
		return cal.get(Calendar.YEAR);
	}
	
//	public int getDayNumber(int day){
//		int today = cal.get(Calendar.DAY_OF_MONTH);
//		cal.set(Calendar.DAY_OF_MONTH, day);
//		int dayNumber= cal.get(Calendar.DAY_OF_WEEK);
//		cal.set(Calendar.DAY_OF_MONTH, today);
//		return dayNumber;
//	}
	
	public int getFirstDay(){
		int today = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int day= cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DAY_OF_MONTH, today);
		return day;
	}
	
	public int getLastDate(){
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public int getMonth(){
		return cal.get(Calendar.MONTH);
	}
	
	
	public boolean isDateClear(LocalDate date){
		if(meetingsByDate.containsKey(date)){			
			return meetingsByDate.get(date).isEmpty();
		}
		return true;
	}
	
	public void addMeeting(Meeting m){
		if(meetingsByDate.containsKey(m.getDate())){
			if(!meetingsByDate.get(m.getDate()).contains(m)){
				meetingsByDate.get(m.getDate()).add(m);
			}
		}else{
			meetingsByDate.put(m.getDate(), new ArrayList<Meeting>());
			meetingsByDate.get(m.getDate()).add(m);
		}
	}
	
	public static void main(String[] args) {
		GeneralCal c = new GeneralCal();
		for (int i = 0; i < 20; i++) {
			int day = c.cal.get(Calendar.DAY_OF_WEEK);
			String dayName = "";
			switch (day) {
			case 2:
				dayName="monday";
				break;
			case 3:
				dayName="tuesday";
				break;
			case 4:
				dayName="wednesday";
				break;
			case 5:
				dayName="thursday";
				break;
			case 6:
				dayName="friday";
				break;
			case 7:
				dayName="saturday";
				break;
			case 1:
				dayName="sunday";
				break;
			}
			System.out.println(dayName+": "+c.cal.get(Calendar.DAY_OF_MONTH));
			c.cal.add(Calendar.DAY_OF_MONTH, 1);
		}


	}
	

}

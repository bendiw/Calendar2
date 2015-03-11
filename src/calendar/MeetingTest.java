package src.calendar;

import org.joda.time.LocalDate;

import no.hal.jex.runtime.JExercise;
import junit.framework.TestCase;
import no.hal.jex.standalone.JexStandalone;

public class MeetingTest extends TestCase {
	
	//duration test
	private int[] duration = {3,30};
	private int[] duration2 = {2,59};
	private Meeting meeting = new Meeting(new LocalDate(2015,03,10), new Person("Per Person",2),"0800","1130","TestMeeting");
	private Meeting meeting2 = new Meeting(new LocalDate(2015,03,10), new Person("Per Person",2),"0850","1149","TestMeeting");
	
	//collides test
	private Meeting m1 = new Meeting(new LocalDate(2015,03,10), new Person("Per Person",2),"0600","0801","Meeting");
	private Meeting m2 = new Meeting(new LocalDate(2015,03,10), new Person("Per Person",2),"0600","0800","Meeting");
	private Meeting m3 = new Meeting(new LocalDate(2015,03,10), new Person("Per Person",2),"1129","1230","Meeting");
	private Meeting m4 = new Meeting(new LocalDate(2015,03,10), new Person("Per Person",2),"1130","1230","Meeting");
	private Meeting m5 = new Meeting(new LocalDate(2015,03,11), new Person("Per Person",2),"0600","0801","Meeting");
	
	@JExercise(
			description="A Meeting must contain a date, a leader, a start, an end, a title"
		)
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
	}
	
	public void testGetDuration() {
		assertEquals(duration[0], meeting.getDuration()[0]);
		assertEquals(duration[1], meeting.getDuration()[1]);
		assertEquals(duration2[0], meeting2.getDuration()[0]);
		assertEquals(duration2[1], meeting2.getDuration()[1]);
	}
	
	public void testCollides(){
		assertEquals(m1.collides(meeting),true);
		assertEquals(m2.collides(meeting),false);
		assertEquals(m3.collides(meeting),true);
		assertEquals(m4.collides(meeting),false);
		assertEquals(m5.collides(meeting),true);
	}
	
	public void testSetStartTime(){
		String startTime = m2.getStartTime();
		testInvalidStartTime("0801", startTime);
		testInvalidStartTime("075", startTime);
		testInvalidStartTime("00759", startTime);
		testInvalidStartTime("07590", startTime);
		testInvalidStartTime("075o", startTime);
		try {
			m2.setStartTime("0759");
			assertEquals("0759", m2.getStartTime());
		} catch (Exception e) {
			fail("0759 is a valid starttime");
		}
	}
	
	public void testSetEndTime(){
		String endTime = meeting.getEndTime();
		testInvalidEndTime("0759", endTime);
		testInvalidEndTime("2401", endTime);
		testInvalidEndTime("080", endTime);
		testInvalidEndTime("08010", endTime);
		testInvalidEndTime("08o1", endTime);
		try {
			meeting.setEndTime("0801");
			assertEquals("0801", meeting.getEndTime());
		} catch (Exception e) {
			fail("0801 is a valid endtime");
		}
	}
	
	private void testExceptionAndValue(Exception e, Class<? extends Exception> c, Object expected, Object actual) {
		assertTrue(e + " should be assignable to " + c, c.isAssignableFrom(e.getClass()));
		assertEquals(expected, actual);
	}
	
	private void testInvalidStartTime(String invalidStart, String existingStart){
		try {
			m2.setStartTime(invalidStart);
			fail(invalidStart + " is an invalid starttime");
		} catch (Exception e) {
			testExceptionAndValue(e, IllegalArgumentException.class, existingStart, m2.getStartTime());			
		}
	}
	
	private void testInvalidEndTime(String invalidEnd, String existingEnd){
		try {
			meeting.setEndTime(invalidEnd);
			fail(invalidEnd + " is an invalid endtime");
		} catch (Exception e) {
			testExceptionAndValue(e, IllegalArgumentException.class, existingEnd, meeting.getEndTime());			
		}
	}
	
	public static void main(String[] args) {
		JexStandalone.main(MeetingTest.class);
	}
}
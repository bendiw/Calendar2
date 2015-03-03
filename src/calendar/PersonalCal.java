package calendar;

import java.util.Map;
import org.joda.time.*;
import java.util.Calendar;

public class PersonalCal extends GeneralCal{
	
	private Person owner;
	
	public PersonalCal(Person p){
		this.owner=p;
	}
	
	public Person getOwner(){
		return this.owner;
	}
	
	public void setOwner(Person p){
		this.owner=p;
	}
	

}

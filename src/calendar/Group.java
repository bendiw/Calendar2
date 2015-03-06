package calendar;

import java.util.ArrayList;

public class Group {
	
	protected ArrayList<Person> members;
	private PersonalCal cal; //Mulig å fjerne denne??
	private String name;
	
	public Group(String name){
		this.name=name;
	}
	

}

package calendar;

public class GroupCal extends GeneralCal{
	
	private Group owner;

	public GroupCal(Group g) {
		this.owner=g;
	}
	
	public Group getGroup(){
		return this.owner;
	}
	
	public void setGroup(Group g){
		this.owner=g;
	}

}

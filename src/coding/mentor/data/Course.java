package coding.mentor.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
	private int id;
	private String name;
	ArrayList<Mentor> teachingmentors;
	private Date begin;
	private Date end;
	private int fee;

		
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Course(int id, String name, List<Mentor> teachingmentors, java.sql.Date begin, java.sql.Date end, int fee) {
		super();
		this.id = id;
		this.name = name;
		this.teachingmentors = new ArrayList<Mentor>();
		this.begin = begin;
		this.end = end;
		this.fee = fee;
	}
		

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Mentor> getTeachingmentors() {
		return teachingmentors;
	}

	public void setTeachingmentors(ArrayList<Mentor> teachingmentors) {
		this.teachingmentors = teachingmentors;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

}

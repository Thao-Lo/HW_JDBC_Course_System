package coding.mentor.data;

import java.util.ArrayList;

public class User {
	private String name;
	private int id;
	private String email;
	private String password;
	private ArrayList<Course> registeredCourses;
	private int failedCount;

	public User() {
		super();		
		// TODO Auto-generated constructor stub
	}

	
	public User(String name, String email, String password) {
		super();		
		this.name = name;
		this.email = email;
		this.password = password;
		this.registeredCourses = new ArrayList<Course>();
	}
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}


	public String getPassword() {
		return password;
	}


	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Course> getRegisteredCourses() {
		return registeredCourses;
	}

	public void setRegisteredCourses(Course course) {
		
		this.registeredCourses.add(course);
	
		
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		
		this.failedCount = failedCount;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

}

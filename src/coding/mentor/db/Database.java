package coding.mentor.db;

import java.util.ArrayList;
import java.util.Date;

import coding.mentor.data.Course;
import coding.mentor.data.Mentor;
import coding.mentor.data.User;

public class Database {
	// create constant that can access everywhere without creating a new object in that class
	public static ArrayList<Mentor> MENTORS_DB = new ArrayList<Mentor>();
	public static ArrayList<Course> COURSES_DB = new ArrayList<Course>();
	public static ArrayList<User> USERS_DB = new ArrayList<User>();
	
	public Database() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	
}

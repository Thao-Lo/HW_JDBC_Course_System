package coding_mentor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import coding.mentor.data.Course;
import coding.mentor.data.Mentor;
import coding.mentor.data.User;
import coding.mentor.db.Database;
import coding.mentor.service.CourseService;
import coding.mentor.service.UserService;

public class Main {
	final static int LOGIN = 1;
	final static int REGISTER = 2;
	final static int COURSE_REGISTER = 1;
	final static int VIEW_MENTOR_DETAIL = 2;
	final static int BACK_TO_COURSE_LIST = 3;

	public static void main(String[] args) throws SQLException {
		// TODO 
		Scanner keyboard = new Scanner(System.in);
		
		
		CourseService courseService = new CourseService();
		UserService userService = new UserService();
		User user = new User();
		
//		Connection connection = DBConnection.makeConnection();
//		System.out.println(connection);	
//		
//		ArrayList<Mentor> mentors = courseService.getAllMentor();
//		for(Mentor mentor: mentors) {
//			System.out.println("Mentor: " + mentor.getName() + " ID " + mentor.getId() + " email " + mentor.getEmail() + " phone " + mentor.getPhone());
//		}
		
		//Database.initDB();

		int selectLoginorRegister = 0;
		String email = null;
		boolean isKeepLogin = false; // đổi tên
		int selectedCourseOption = 0;
		int userId = 0;

		// LOGIN + REGISTER
		do {
			showHomePage();
			selectLoginorRegister = Integer.parseInt(keyboard.nextLine());
			System.out.println("-----------------------");
			switch (selectLoginorRegister) {

			case REGISTER:
				isKeepLogin = doRegister(email, userService, isKeepLogin);
				break;

			case LOGIN:
				
				System.out.print("-- Email: ");
                email = keyboard.nextLine();
                
                System.out.print("-- Password: ");
                String password = keyboard.nextLine();
                
                isKeepLogin = userService.login(email, password);

                if (isKeepLogin) {
                    // If login is successful, find the userId using the entered email
                    userId = userService.findUser(email);
                }

                break;
			}

		} while (isKeepLogin == false);
		

		do {

			selectedCourseOption = showCourseListSelection(courseService, userService, userId);

		} while (selectedCourseOption != VIEW_MENTOR_DETAIL);

	}

	public static int showCourseListSelection(CourseService courseService, UserService userService, int userId) throws SQLException {
		Scanner keyboard = new Scanner(System.in);
		int courseId = 0;
		int selectedCourseOption = 0;
		do {
			System.out.println("***COURSE LIST");
			System.out.println("0 - Your course");
			courseService.showAllCourses();
			System.out.print("Enter the course id: ");
			courseId = keyboard.nextInt();

			if (courseId == 0) {
				userService.showRegisterCoursesByUser(userId);
				break;
			}
		} while (courseId == 0);

		if (courseId > 0 && courseId < 5) {
			courseService.showCourseDetails(courseId);
			System.out.println("-------------------");

			showCourseOption();
			selectedCourseOption = keyboard.nextInt();
			switch (selectedCourseOption) {
			case COURSE_REGISTER:				
				userService.registerNewCourse(courseId, userId);				
				break;
			case VIEW_MENTOR_DETAIL:
				courseService.showMentorByCourse(courseId);				
				break;
			case BACK_TO_COURSE_LIST:
				break;
			}
		}
		return selectedCourseOption;

	}

	public static boolean doRegister(String email, UserService userService, boolean isKeepLogin) throws SQLException {

		Scanner keyboard = new Scanner(System.in);
		System.out.println("***REGISTER");
		System.out.print("- Enter your Name: ");
		String name = keyboard.nextLine();
		System.out.print("- Enter your Email: ");
		email = keyboard.nextLine();
		System.out.print("- Enter your Password: ");
		String password = keyboard.nextLine();
		userService.registerNewUser(name, email, password);

		return isKeepLogin;
	}



	public static void showHomePage() {

		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.print("Please choose Login or Register: ");
	}

	public static void showCourseOption() {
		System.out.println("1. Register");
		System.out.println("2. View mentor details");
		System.out.println("3. Back to course list");
	}
}

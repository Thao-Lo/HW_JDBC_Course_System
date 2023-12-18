package coding.mentor.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import coding.mentor.data.Course;
import coding.mentor.data.Mentor;
import coding.mentor.data.User;
import coding.mentor.db.Database;
import coding_mentor.DBConnection;

public class UserService {
	// chi chua nhung functions phuc vu cho class user

	public void registerNewUser(String name, String email, String password) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		PreparedStatement preStmt = null;

		// Check if email is already registered
		if (isEmailRegistered(connection, email)) {
			System.out.println(" - Email is unavailable");
			System.out.println(" - Please register again with a different email");
			return; // Exit the method if email is not available
		}

		String SQL = "INSERT INTO user (`name`, `email`, `password`) VALUES (?, ?, ?);";
		preStmt = connection.prepareStatement(SQL);

		preStmt.setString(1, name);
		preStmt.setString(2, email);
		preStmt.setString(3, password);

		preStmt.executeUpdate();

		System.out.println("Register successfully!!!");

	}

	private boolean isEmailRegistered(Connection connection, String email) throws SQLException {
		String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setString(1, email);
		ResultSet resultSet = preStmt.executeQuery();
		if (resultSet.next()) {
			int count = resultSet.getInt(1);
			return count > 0;
		}
		return false;
	}

	public int findUser(String email) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		String sql = "SELECT id FROM user WHERE email = ?";
		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setString(1, email);

		ResultSet rsEmail = preStmt.executeQuery();

		int id = 0;

		if (rsEmail.next()) {
			id = rsEmail.getInt("id");
			return id;
		} else {
			return -1;
		}
	}

	public boolean login(String email, String password) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		String sql = "SELECT u.* FROM user u WHERE email = ?";

		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setString(1, email);

		ResultSet rsLogin = preStmt.executeQuery();

		if (!rsLogin.next()) {
			System.out.println("Email not found. Please enter a valid email");
			return false;
		}

		User user = new User();
		user.setEmail(rsLogin.getString("email"));
		user.setPassword(rsLogin.getString("password"));
		user.setFailedCount(rsLogin.getInt("failedCount"));

		if (!user.getPassword().equals(password)) {
			System.out.println("Incorrect password. Please try again.");
			updateFailedCount(user.getEmail());
			return false;
		}

		if (user.getFailedCount() >= 4) {
			System.out.println("login failed. You're account is locked");
			return false;

		} else {
			System.out.println("Login successfully");
		}

		return true;
	}

	public void updateFailedCount(String email) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		String sql = "UPDATE user SET `failedCount` = failedCount + 1 WHERE email = ?";

		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setString(1, email);

		preStmt.executeUpdate();

	}

	public void showRegisterCoursesByUser(int id) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		String sql = "SELECT uc.* FROM user u " + "JOIN user_course uc ON uc.user_id = u.id " + "WHERE u.id = ?; ";
		CourseService courseService = new CourseService();

		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setInt(1, id);

		ResultSet rsUserCourse = preStmt.executeQuery();

		ArrayList<Course> userCourses = new ArrayList<Course>();
		while (rsUserCourse.next()) {
			Course course = new Course();
			course.setId(rsUserCourse.getInt("course_id"));
			userCourses.add(course);
		}

		System.out.println("**YOUR COURSES: ");

		for (Course course : userCourses) {
			if (course.getId() == 0) {
				System.out.println("please register a new course");
			} else {
				courseService.showCourseDetails(course.getId());
			}
		}

	}

	public void registerNewCourse(int courseId, int userId) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		String sql = "SELECT * FROM user_course WHERE user_id = ?;";

		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setInt(1, userId);

		ResultSet rsUser = preStmt.executeQuery();

		while (rsUser.next()) {
			if (rsUser.getInt("course_id") == 0) {
				updateNewCourse(connection, courseId, userId);
				System.out.println("Register new course successfully");

				return;
			}
			if (rsUser.getInt("course_id") == courseId) {
				System.out.println("you already registered this course");
				return;
			}
			if (rsUser.getInt("course_id") != courseId) {
				insertNewCourse(connection, userId, courseId);
				System.out.println("Register new course successfully");
				return;
			}
		}
	}

	public void updateNewCourse(Connection connection, int courseId, int userId) throws SQLException {
		String sql = "UPDATE user_course SET course_id = ? WHERE (`user_id` = ?) and (`course_id` = '0');";
		PreparedStatement preStmt = connection.prepareStatement(sql);

		preStmt.setInt(1, courseId);
		preStmt.setInt(2, userId);

		preStmt.executeUpdate();

	}

	public void insertNewCourse(Connection connection, int userId, int courseId) throws SQLException {
		String sql = "INSERT INTO user_course (`user_id`, `course_id`) VALUES (?, ?);";
		PreparedStatement preStmt = connection.prepareStatement(sql);

		preStmt.setInt(1, userId);
		preStmt.setInt(2, courseId);

		preStmt.executeUpdate();
	}

}

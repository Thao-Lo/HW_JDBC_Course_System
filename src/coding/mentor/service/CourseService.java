package coding.mentor.service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coding.mentor.data.Course;
import coding.mentor.data.Mentor;
import coding.mentor.db.Database;
import coding_mentor.DBConnection;

public class CourseService {
	final static int BE1 = 1;
	final static int BE2 = 2;
	final static int DATA1 = 3;
	final static int FE1 = 4;

	public void showAllCourses() throws SQLException {
		Connection connection = DBConnection.makeConnection();

		String SQL = "SELECT * FROM course;";
		Statement stmt = connection.createStatement();

		ResultSet resultSet = stmt.executeQuery(SQL);
		ArrayList<Course> courses = new ArrayList<Course>();

		while (resultSet.next()) {
			Course course = new Course();

			course.setId(resultSet.getInt("id"));
			course.setName(resultSet.getString("name"));
			course.setBegin(resultSet.getDate("begin"));
			course.setEnd(resultSet.getDate("end"));
			course.setFee(resultSet.getInt("fee"));
			course.setTeachingmentors(getMentorByCourseId(course.getId(), connection));
			courses.add(course);
		}

		// TODO
		for (int i = 0; i < courses.size(); i++) {

			System.out.print(i + 1 + " - " + courses.get(i).getName());
			for (Mentor mentor : courses.get(i).getTeachingmentors()) {
				System.out.print(" -- Mentor Name: " + mentor.getName());
			}
			System.out.println();
		}
	}

	public ArrayList<Mentor> getMentorByCourseId(int courseId, Connection connection) throws SQLException {

		String sql = "SELECT m.* FROM course c " + "JOIN course_mentor cm ON c.id = cm.course_id "
				+ "JOIN mentor m ON m.id = cm.mentor_id " + "WHERE c.id = ?;";

		ArrayList<Mentor> mentors = new ArrayList<Mentor>();
		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setInt(1, courseId);

		ResultSet rsMentor = preStmt.executeQuery();

		while (rsMentor.next()) {
			Mentor mentor = new Mentor();
			mentor.setName(rsMentor.getString("name"));
			mentor.setEmail(rsMentor.getString("email"));
			mentor.setPhone(rsMentor.getString("phone"));
			mentors.add(mentor);
		}
		return mentors;

	}

	public ArrayList<Mentor> getAllMentor() throws SQLException {
		Connection connection = DBConnection.makeConnection();

		String SQL = "SELECT * FROM mentor";
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(SQL);
		ArrayList<Mentor> mentors = new ArrayList<Mentor>();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String phone = resultSet.getString("phone");
			Mentor mentor = new Mentor(id, name, email, phone);
			mentors.add(mentor);
		}
		return mentors;
	}

	public void showCourseDetails(int id) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		String sql = "SELECT c.*, group_concat(m.name) as mentorName FROM course c JOIN course_mentor cm ON c.id = cm.course_id JOIN mentor m ON m.id = cm.mentor_id WHERE c.id = ? "
				+ "GROUP BY c.id, c.name, c.begin, c.end, c.fee";

		PreparedStatement preStmt = connection.prepareStatement(sql);
		preStmt.setInt(1, id);

		ResultSet resultSet = preStmt.executeQuery();

		ArrayList<Course> courses = new ArrayList<Course>();

		while (resultSet.next()) {
			Course course = new Course();
			course.setId(resultSet.getInt("id"));
			course.setName(resultSet.getString("name"));
			course.setBegin(resultSet.getDate("begin"));
			course.setEnd(resultSet.getDate("end"));
			course.setFee(resultSet.getInt("fee"));
			course.setTeachingmentors(getMentorByCourseId(course.getId(), connection));

			courses.add(course);
		}
		// TODO
		System.out.println("***COURSE DETAIL");
		for (Course course : courses) {
			System.out.println("- Course name: " + course.getName());
			for (Mentor mentor : course.getTeachingmentors()) {
				System.out.println("- Mentor Name: " + mentor.getName());
			}
			System.out.println("- Course start date: " + course.getBegin());
			System.out.println("- Course end date: " + course.getEnd());
			System.out.println("- Course fee: " + course.getFee());
		}
	}

	public void showMentorByCourse(int id) throws SQLException {
		Connection connection = DBConnection.makeConnection();
		System.out.println("***MENTOR DETAILS");

		for (Mentor mentor : getMentorByCourseId(id, connection)) {
			System.out.println("- Mentor name: " + mentor.getName());
			System.out.println("- Mentor phone number: " + mentor.getPhone());
			System.out.println(("- Mentor email: " + mentor.getEmail()));
			System.out.println();
		}
	}
}

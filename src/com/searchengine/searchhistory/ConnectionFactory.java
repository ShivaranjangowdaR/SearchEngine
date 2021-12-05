package com.searchengine.searchhistory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static String url;
	private static String user;
	private static String password;

	static {
		try {
			String driverClassName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverClassName);
			url = "jdbc:mysql://localhost:3306/search";
			user = "root";
			password = "Shivu@110499";

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Connection create() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;

	}

}

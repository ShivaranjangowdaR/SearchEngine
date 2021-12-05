package com.searchengine.deleteHistory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.searchengine.searchhistory.ConnectionFactory;

public class DeleteHistory {
	Scanner sc = new Scanner(System.in);
 public void Delete() {
		System.out.println("Enter file name for delete in database");
		String filename= sc.nextLine();
		Connection connection= null;
		connection=	ConnectionFactory.create();

		try {
			Statement st = connection.createStatement();
			String query = "delete from fileinformation1 where fileName='"+filename+"'";
			int rs=st.executeUpdate(query);
			if (rs!=0) {
				
					System.out.println( "File deleted");

			 }
			 else
			 {
				 System.out.println( "File not found");
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close the connection

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					  e.printStackTrace();
				}
			}
		}
	 
 }
}

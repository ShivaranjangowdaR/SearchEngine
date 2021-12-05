package com.searchengine.recentsearch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.saerchengine.TestMain;
import com.searchengine.searchhistory.ConnectionFactory;



public class Lastsearch implements MostFrequentSearch {
	
	static Logger log = Logger.getLogger(TestMain.class);
	

	@Override
	public void process() {
		// TODO Auto-generated method stub
		Connection connection= null;
		connection=	ConnectionFactory.create();
		
		try {
			Statement st = connection.createStatement();
			String query = "SELECT distinct * From fileinformation";
			ResultSet rs = st.executeQuery(query);
			 if (rs != null) {
				 while (rs.next()) {
					 
					System.out.println( rs.getString("fileName")+" : "+rs.getString("pathsFound"));
				 }

			 }
			 else
			 {
				 log.info("File not exist in databse");
				 System.out.println( "empty");
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
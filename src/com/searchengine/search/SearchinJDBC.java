package com.searchengine.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saerchengine.TestMain;
import com.searchengine.searchhistory.ConnectionFactory;

public class SearchinJDBC implements ISearchManager {
	private List<String> pathFound = new ArrayList<>();
	static Logger log = Logger.getLogger(TestMain.class);
	@Override
	public List<String> search(String fileName, List<String> drives) {
		// TODO Auto-generated method stub
		Connection connection = null;
		connection = ConnectionFactory.create();
		try {
			Statement stmt = connection.createStatement();
			String query[] = { "select Path from  fileinformation where Path != '[]' and Filename= ('"
					+ fileName + "')" };
			for (String q : query) {
				ResultSet rs = stmt.executeQuery(q);
				while (rs.next()) {
					String path = rs.getString("Path");
					pathFound.add(path);
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Filename not found in database");	}

		return pathFound;
	}
}

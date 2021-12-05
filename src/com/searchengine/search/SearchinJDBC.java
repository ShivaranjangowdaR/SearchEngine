package com.searchengine.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.searchengine.searchhistory.ConnectionFactory;

public class SearchinJDBC implements ISearchManager {
	private List<String> pathFound = new ArrayList<>();

	@Override
	public List<String> search(String fileName, List<String> drives) {
		// TODO Auto-generated method stub
		Connection connection = null;
		connection = ConnectionFactory.create();
		try {
			Statement stmt = connection.createStatement();
			String query[] = { "select pathsFound from  fileinformation where pathsFound != '[]' and fileName= ('"
					+ fileName + "')" };
			for (String q : query) {
				ResultSet rs = stmt.executeQuery(q);
				while (rs.next()) {
					String path = rs.getString("pathsFound");
					pathFound.add(path);
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pathFound;
	}
}

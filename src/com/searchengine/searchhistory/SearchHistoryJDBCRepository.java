package com.searchengine.searchhistory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.saerchengine.exception.UnableToExececuteException;

public class SearchHistoryJDBCRepository implements ISearchHistoryRepository {

	public boolean storeSearchResult(String fileName, List<String> drives, List<String> pathsFound)
			throws UnableToExececuteException {

		boolean isStored = true;

		Connection connection = null;
		connection = ConnectionFactory.create();
		try {
			Statement smt = connection.createStatement();
			String q = "Select Count(*) from fileinformation where fileName ='" + fileName + "' and pathsFound='"
					+ pathsFound + "'";
			ResultSet res = smt.executeQuery(q);
			if (res.next()) {
				System.out.println("File already saved");
			} else {
				String query = "insert into fileinformation values ('" + fileName + "','" + drives + "','" + pathsFound
						+ "')";
				isStored = smt.execute(query);
			}

		} catch (SQLException e) {
			throw new UnableToExececuteException();
		} catch (Exception e) {
			throw new UnableToExececuteException();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return isStored;

	}

}

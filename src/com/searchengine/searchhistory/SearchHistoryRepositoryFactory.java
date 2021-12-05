package com.searchengine.searchhistory;

public class SearchHistoryRepositoryFactory {
	public static ISearchHistoryRepository create() {

		return new SearchHistoryJDBCRepository();
	}

}

package com.searchengine.search;

import org.apache.log4j.Logger;

import com.saerchengine.exception.InvalidRootFinderException;

public class SearchJDBCFactory {
	static Logger log = Logger.getLogger(SearchJDBCFactory.class);

	public static ISearchManager create() throws InvalidRootFinderException {
		return new SearchinJDBC();
	}

}
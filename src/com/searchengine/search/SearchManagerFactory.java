package com.searchengine.search;

import org.apache.log4j.Logger;

import com.saerchengine.exception.InvalidRootFinderException;

public class SearchManagerFactory {
	static Logger log = Logger.getLogger(SearchManagerFactory.class);
	public static ISearchManager create() throws InvalidRootFinderException {
		return  new SearchManger();
	}
	
}



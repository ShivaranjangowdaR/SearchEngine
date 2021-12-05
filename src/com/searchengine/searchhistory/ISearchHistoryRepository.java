package com.searchengine.searchhistory;

import java.util.List;

import com.saerchengine.exception.UnableToExececuteException;

public interface ISearchHistoryRepository {
	boolean storeSearchResult(String fileName, List<String> drives, List<String> pathsFound)
			throws UnableToExececuteException;

}

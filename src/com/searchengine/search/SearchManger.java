package com.searchengine.search;

import java.util.ArrayList;
import java.util.List;

import com.saerchengine.exception.UnableToExececuteException;
import com.searchengine.searchhistory.ISearchHistoryRepository;
import com.searchengine.searchhistory.SearchHistoryRepositoryFactory;

public class SearchManger implements ISearchManager {

	private List<String> pathFound = new ArrayList<>();

	@Override
	public List<String> search(String fileName, List<String> drives) {
		// TODO Auto-generated method stub

		Thread[] thread = new Thread[drives.size()];
		FileSearcher[] searches = new FileSearcher[drives.size()];

		for (int i = 0; i < drives.size(); i++) {
			FileSearcher fileSearcher = new FileSearcher(fileName, drives.get(i));
			searches[i] = fileSearcher;
			thread[i] = new Thread(fileSearcher);
			thread[i].start();
		}

		for (Thread thread1 : thread) {
			try {
				thread1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < searches.length; i++) {
			for (String path : searches[i].getPathsFound()) {
				pathFound.add(path);
			}
		}

		storeSearchResult(fileName, drives, pathFound);

		return pathFound;
	}

	protected boolean storeSearchResult(String fileName, List<String> drives, List<String> patshFound) {

		boolean isStored = true;

		ISearchHistoryRepository historyRepository = SearchHistoryRepositoryFactory.create();
		try {
			isStored = historyRepository.storeSearchResult(fileName, drives, patshFound);
		} catch (UnableToExececuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isStored;

	}

}

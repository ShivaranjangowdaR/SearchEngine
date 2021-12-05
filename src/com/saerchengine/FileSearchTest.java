package com.saerchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.saerchengine.exception.InvalidRootFinderException;
import com.searchengine.search.ISearchManager;
import com.searchengine.search.SearchManagerFactory;

class FileSearchTest {

	@Test
	void test() {
		TestMain test = new TestMain();
		try {
			List<String> drives;
			drives = test.detectDrives();
			String file=test.getFileName();
			ISearchManager searchManger1 = SearchManagerFactory.create();
			List<String> actual= searchManger1.search(file, drives);
			List<String>excpect = new ArrayList<String>();;
			excpect.add("C:\\New folder\\New.txt");
			assertEquals(excpect,actual);
		} catch (InvalidRootFinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

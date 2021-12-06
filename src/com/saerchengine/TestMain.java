package com.saerchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.saerchengine.exception.InvalidRootFinderException;
import com.saerchengine.exception.UnableToExececuteException;
import com.searchengine.notifications.Login;
import com.searchengine.notifications.Logout;
import com.searchengine.recentsearch.Lastsearch;
import com.searchengine.recentsearch.MostFrequentSearch;
import com.searchengine.rootfinder.IRootFinder;
import com.searchengine.rootfinder.RootFinderFactory;
import com.searchengine.search.ISearchManager;
import com.searchengine.search.SearchJDBCFactory;
import com.searchengine.search.SearchManagerFactory;
import com.searchengine.searchhistory.ISearchHistoryRepository;
import com.searchengine.searchhistory.SearchHistoryJDBCRepository;

public class TestMain {
	static Logger log = Logger.getLogger(TestMain.class);
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		Login login = new Login();
		System.out.println("Enter 1 for Register or enter 2 for login");
		int choice = sc.nextInt();
		if (choice == 1) {
			login.reg();
			System.exit(0);
		} else if (choice == 2) {
			login.login();
		}
		String filename = getFileName();
		List<String> drives = null;
		try {
			drives = detectDrives();
		} catch (InvalidRootFinderException e) {
			// TODO Auto-generated catch block
			log.error("Drives not found");
			e.printStackTrace();
			log.info("Detecting drives stopped");
			System.exit(0);
		}

		log.info("Drives are Detecting....");
		showDrives(drives);

		

		ISearchManager searchManger;
		try {
			log.info("Searching file in Database");
			searchManger = SearchJDBCFactory.create();
			List<String> pathFound = searchManger.search(filename, drives);
			String paths = showpaths(pathFound);
			if (paths == null) {
				log.info("Searching file in Device");
				searchManger = SearchManagerFactory.create();
				List<String> pathFound1 = searchManger.search(filename, drives);
				String paths1 = showpaths(pathFound1);
				try {
					ISearchHistoryRepository repository = new SearchHistoryJDBCRepository();
					repository.storeSearchResult(filename, drives, pathFound1);
					if (paths1 == null) {
						log.error("path not found in Device");
					} else {
						log.error("path found in Device ");
						System.out.println(paths1);
					}

				} catch (UnableToExececuteException e) { // TODO Auto-generated catch block
				 	e.printStackTrace();
				}
			} else {
				log.info("path found in Database ");
				System.out.println(paths);
			}
		} catch (InvalidRootFinderException e1) {
			// TODO Auto-generated catch block
			log.error("Unable to search File");
			e1.printStackTrace();
		}
		log.info("Files which are presented in database");
		MostFrequentSearch mylambda = () -> {
			System.out.println("Most Frequent Searched File");
			Lastsearch r = new Lastsearch();
			r.process();
		};
		mylambda.process();

		System.out.println("===========================================================================================================");

		Logout logout = new Logout();
		logout.logout();

	}

	private static String showpaths(List<String> paths) {
		// TODO Auto-generated method stub
		String pathI = null;
		for (String path : paths) {
			pathI = path;
		}

		return pathI;

	}

	public static String getFileName() {
		// TODO Auto-generated method stub

		String filename = null;
		System.out.println("Enter the file name to serach ");
		filename = sc.next();
		return filename;
	}

	public static void showDrives(List<String> drives) {
		// TODO Auto-generated method stub
		System.out.println("Drives detected in you system");
		for (String drive : drives) {
			System.out.println(drive);
		}
	}

	public static List<String> detectDrives() throws InvalidRootFinderException {

		List<String> drives = new ArrayList<String>();

		System.out.println("Enter  choice 1 for search in all drives in system or Enter choice 2 for serach inn active drives");

		int choice = sc.nextInt();
		IRootFinder finder = null;
		finder = RootFinderFactory.create(choice);
		drives = finder.detectDrives();

		return drives;
	}

}

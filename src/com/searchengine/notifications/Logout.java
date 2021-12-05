package com.searchengine.notifications;

import java.util.Scanner;

import com.saerchengine.TestMain;
import com.saerchengine.exception.InvalidRootFinderException;
import com.searchengine.concurrency.FileSearchConcurrent;

public class Logout {
	static Scanner sc = new Scanner(System.in);

	public void logout() throws InvalidRootFinderException {
		System.out.println(
				"Enter 1 for logout or enter 2 fro continue searching or enter 3 for searching using concurrency");
		int choice = sc.nextInt();
		if (choice == 1) {
			System.out.println("Thank you....");
			System.exit(0);
		} else if (choice == 2) {
			TestMain test = new TestMain();
			try {
				test.main(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (choice == 3) {
			String filename = sc.nextLine();
			FileSearchConcurrent con = new FileSearchConcurrent(filename, null, 2);
			try {
				con.WriteConsoleOutputToFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			throw new InvalidRootFinderException("Wrong choice");
		}
	}

}

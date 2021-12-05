package com.searchengine.rootfinder;

import org.apache.log4j.Logger;

import com.saerchengine.exception.InvalidRootFinderException;

public class RootFinderFactory {
	static Logger log = Logger.getLogger(RootFinderFactory.class);

	public static IRootFinder create(int choice) throws InvalidRootFinderException {
		IRootFinder finder = null;
		if (choice == 1) {
			finder = new AllRootFinder();
		} else if (choice == 2) {
			finder = new ActiveRootFinder();
		} else {
			log.error("You enterd wrong choice");
			throw new InvalidRootFinderException("Wrong choice");
		}

		return finder;
	}

}

package com.saerchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.saerchengine.exception.InvalidRootFinderException;

class Drivestesting {

	@Test
	void test() {
		TestMain test = new TestMain();
		try {
			List<String> actual = test.detectDrives();
			List<String> excepted = new ArrayList<String>();
			excepted.add("C:\\");
			excepted.add("D:\\");
			assertEquals(excepted, actual);
		} catch (InvalidRootFinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

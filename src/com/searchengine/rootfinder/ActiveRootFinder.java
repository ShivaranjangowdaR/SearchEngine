package com.searchengine.rootfinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActiveRootFinder implements IRootFinder {

	@Override
	public List<String> detectDrives() {
		// TODO Auto-generated method stub
		File[] roots = File.listRoots();
		List<String> drives = new ArrayList<>();

		for (File root : roots) {
			if (root.canRead())
				drives.add(root.getAbsolutePath());
		}
		return drives;
	}

}

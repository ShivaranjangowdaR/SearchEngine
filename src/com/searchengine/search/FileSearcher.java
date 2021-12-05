package com.searchengine.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher implements Runnable {
	
	private String filename;
	private String drive;
	private List<String> pathsFound;
	
	public String getFilename() {
		return filename;
	}
	public String getDrive() {
		return drive;
	}
	
	public List<String> getPathsFound() {
		return pathsFound;
	}
	public FileSearcher(String filename, String string) {
		super();
		this.filename = filename;
		this.drive = string;
		pathsFound = new ArrayList<>();
	}
	
	public void search(String fileName,String drive) {
		File drives = new File(drive); 
		File[] listOfFiles = drives.listFiles();
		if(listOfFiles != null) {
			for(File file: listOfFiles) {
				if(file.isDirectory()) {
					String content = file.toString();
					search(fileName,content);
				}else {
					if(file.getName().equalsIgnoreCase(fileName)) {
						pathsFound.add(file.getAbsolutePath());
					}
				}
			}
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		search(this.getFilename(),this.getDrive());
		
	}
	
}

package com.searchengine.concurrency;



	import java.io.File;
	import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
	import java.util.concurrent.Callable;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	import java.util.concurrent.Future;
	import java.util.concurrent.LinkedBlockingQueue;
	import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.saerchengine.TestMain;
import com.searchengine.notifications.Logout;
	public class FileSearchConcurrent {
		static Logger log = Logger.getLogger(TestMain.class);
		static File filecall = null;

		private static class RunnableDirSearch implements Runnable {
			private final BlockingQueue<File> dirQueue;
			private final BlockingQueue<File> fileQueue;
			private final AtomicLong count;
			private final int num;

			public RunnableDirSearch(final BlockingQueue<File> dirQueue, final BlockingQueue<File> fileQueue,
					final AtomicLong count, final int num) {
				this.dirQueue = dirQueue;
				this.fileQueue = fileQueue;
				this.count = count;
				this.num = num;
			}

			public void run() {
				try {
					File dir = dirQueue.take();
					while (dir != filecall) {
						final File[] fi = dir.listFiles();
						if (fi != null) {
							for (final File element : fi) {
								if (element.isDirectory()) {
									count.incrementAndGet();
									dirQueue.put(element);
								} else {
									fileQueue.put(element);
								}
							}
						}
						final long c = count.decrementAndGet();
						if (c == 0) {
							end();
						}
						dir = dirQueue.take();
					}
				} catch (final InterruptedException ie) {
				}
			}

			private final void end() {
				try {
					fileQueue.put(filecall);
				} catch (final InterruptedException e) {
				}
				for (int i = 0; i < num; i++) {
					try {
						dirQueue.put(filecall);
					} catch (final InterruptedException e) {
					}
				}
			}
		}

		private static class CallableFileSearch implements Callable<File> {
			private final BlockingQueue<File> dirQueue;
			private final BlockingQueue<File> fileQueue;
			private final String name;
			private final int num;

			public CallableFileSearch(final BlockingQueue<File> dirQueue, final BlockingQueue<File> fileQueue,
					final String name, final int num) {
				this.dirQueue = dirQueue;
				this.fileQueue = fileQueue;
				this.name = name;
				this.num = num;
			}

			public File call() throws Exception {
				File file = fileQueue.take();
				while (filecall != file) {
					final String filename = file.getName().toLowerCase();
					final String lf = name.toLowerCase();
					if (filename.equalsIgnoreCase(name) || filename.startsWith(lf) || filename.endsWith(lf)) {
						end();
						return file;
					}
					file = fileQueue.take();
				}
				return null;
			}

			private final void end() {
				for (int i = 0; i < num; i++) {
					try {
						dirQueue.put(filecall);
					} catch (final InterruptedException e) {
					}
				}
			}
		}

		private final String filename;
		private final File baseDir;
		private final int concurrency;
		private final AtomicLong count;

		public FileSearchConcurrent(final String fileName, final File baseDir, final int concurrency) {
			this.filename = fileName;
			this.baseDir = baseDir;
			this.concurrency = concurrency;
			count = new AtomicLong(0);
			filecall = new File(fileName);
		}

		public String find() {
			final ExecutorService ex = Executors.newFixedThreadPool(concurrency + 1);
			final BlockingQueue<File> dirQueue = new LinkedBlockingQueue<File>();
			final BlockingQueue<File> fileQueue = new LinkedBlockingQueue<File>(10000);
			for (int i = 0; i < concurrency; i++) {
				ex.submit(new RunnableDirSearch(dirQueue, fileQueue, count, concurrency));
			}
			count.incrementAndGet();
			dirQueue.add(baseDir);
			final Future<File> c = ex.submit(new CallableFileSearch(dirQueue, fileQueue, filename, concurrency));
			try {
				
				return c.get().getAbsolutePath();
			} catch (final Exception e) {
				return "File is not available";
			} finally {
				ex.shutdownNow();
			}
		}


		public static void WriteConsoleOutputToFile() throws Exception {
			TestMain test = new TestMain(); 
			String filename=test.getFileName();
		    File filecall = new File(filename);
		    File[] drives = File.listRoots();
		    Scanner sc = new Scanner(System.in);
		    System.out.println("Enter drive you need to search");
		    String drive=sc.nextLine();
		    log.info("Searching file using concurrency");
			if (drives != null && drives.length > 0) {
				final File aDrive = new File(drive);
				log.info("Searching file in Drive");
				final FileSearchConcurrent ff = new FileSearchConcurrent(filecall.getName(), aDrive, 6);
				final long ini = System.currentTimeMillis();
				final String f = ff.find();

				final long end = System.currentTimeMillis();
				System.out.println(f + " \n" + "Time: " + (end - ini) + " ms");
				PrintStream printStream = new PrintStream("D:\\output.txt");
				System.setOut(printStream);
				System.out.println(f + " \n" + "Time: " + (end - ini) + " ms");
				  Logout logout = new Logout();
				  logout.logout();
			}
		}

	}




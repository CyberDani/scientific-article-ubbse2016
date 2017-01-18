package edu.uci.ics.crawler4j.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	private String crawlStorageFolder;
	private int numberOfCrawlers;
	private String storageFolder;
	private String seedsFile;
	private CrawlController controller;

	/**
	 * Controller constructor
	 * 
	 * @param crawlStorageFolder 
	 * @param numberOfCrawlers
	 * @param storageFolder
	 * @param seedsFile
	 */
	public Controller(String crawlStorageFolder, int numberOfCrawlers, String storageFolder, String seedsFile) {
		this.crawlStorageFolder = crawlStorageFolder;
		this.numberOfCrawlers = numberOfCrawlers;
		this.storageFolder = storageFolder;
		this.seedsFile = seedsFile;
	}

	/**
	 * Get the list of seeds from the seedsFile
	 * 
	 * @return list of seeds
	 */
	private List<String> getSeeds() {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(seedsFile);
			br = new BufferedReader(fr);

			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				list.add(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return list;
	}
	
	/**
	 * 
	 * Stop crawler
	 * 
	 */
	public void stopCrawler() {
		controller.shutdown();
	}
	
	/**
	 * Run crawler
	 * 
	 * @throws Exception
	 */
	public void runCrawler() throws Exception {
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setIncludeBinaryContentInCrawling(true);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, we need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */

		List<String> seeds = getSeeds();
		for (String seed : seeds) {
			controller.addSeed(seed);
		}

		/*
		 * Start the crawl. This is a blocking operation, meaning that the code
		 * will reach the line after this only when crawling is finished.
		 */
		MyCrawler.configure(storageFolder);
		controller.start(MyCrawler.class, numberOfCrawlers);
	}

}

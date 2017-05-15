package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.util.SystemOutLogger;

import common.PDFContainer;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.main.Controller;
import edu.uci.ics.crawler4j.main.MyCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import frontend.app.TextProcessor;

public class Test {
	@org.junit.Test
	public void getSeeds_notEmpty_returnsSeeds() {
		String crawlStorageFolder = "storage";
		int numberOfCrawlers = 7;
		String storageFolder = "crawl";
		String seedsFile = "src/testData/seeds1.txt";
		Controller c = new Controller(crawlStorageFolder, numberOfCrawlers, storageFolder, seedsFile);
		List<String> seeds = c.getSeeds();
		ArrayList<String> mySeeds = new ArrayList<String>(
			    Arrays.asList("www.facebook.com", "www.flowers.com", "www.sky.com"));
		assertEquals(seeds, mySeeds);
	}
	
	@org.junit.Test
	public void shouldVisit_urlInPage_returnsTrue() {
		WebURL pageUrl = new WebURL();
		pageUrl.setURL("http://www.cs.ubbcluj.ro/");
		Page page = new Page(pageUrl);
		
		WebURL url = new WebURL();
		url.setURL("http://www.cs.ubbcluj.ro/hu/");
		
		MyCrawler crawler = new MyCrawler();
		Boolean visit = crawler.shouldVisit(page, url);
		assertEquals(visit, true);
	}
	
	@org.junit.Test
	public void shouldVisit_urlNotInPage_returnsFalse() {
		WebURL pageUrl = new WebURL();
		pageUrl.setURL("http://www.cs.ubbcluj.ro");
		Page page = new Page(pageUrl);
		
		WebURL url = new WebURL();
		url.setURL("http://www.google.com");
		
		MyCrawler crawler = new MyCrawler();
		Boolean visit = crawler.shouldVisit(page, url);
		assertEquals(visit, false);
	}

	@org.junit.Test
	public void shouldVisit_goodUrl_returnsTrue() {
		WebURL pageUrl = new WebURL();
		pageUrl.setURL("http://vfu.bg/en");
		Page page = new Page(pageUrl);
		
		WebURL url = new WebURL();
		url.setURL("http://vfu.bg/en/e-Learning/Computer-Basics--computer_basics2.pdf");
		
		MyCrawler crawler = new MyCrawler();
		Boolean visit = crawler.shouldVisit(page, url);
		assertEquals(visit, true);
	}
	
	@org.junit.Test
	public void shouldVisit_notGoodUrl_returnsFalse() {
		WebURL pageUrl = new WebURL();
		pageUrl.setURL("https://sdo.gsfc.nasa.gov");
		Page page = new Page(pageUrl);
		
		WebURL url = new WebURL();
		url.setURL("https://sdo.gsfc.nasa.gov/assets/img/latest/latest_1024_0193.jpg");
		
		MyCrawler crawler = new MyCrawler();
		Boolean visit = crawler.shouldVisit(page, url);
		assertEquals(visit, false);
	}
	
	@org.junit.Test
	public void numberOfImages_numOfImages_returnsNumOfImages() {
		File selectedFile = new File("src/testData/Lu Liu Cong.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);

		int expectedNumOfImages = 0;
		int numOfImages = tp.getImageNumberFromPDF(tp.getPdDocument());

		assertEquals(expectedNumOfImages, numOfImages);
	}
	

	@org.junit.Test
	public void bibliographyExistence_bibilographyNotExists_returnsFalse() {
		File selectedFile = new File("src/testData/car_speaker.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);

		Boolean expected = false;
		Boolean bibliography = tp.bibliographyExistence();

		assertEquals(expected, bibliography);
	}
	
	@org.junit.Test
	public void bibliographyExistence_bibilographyExists_returnsTrue() {
		File selectedFile = new File("src/testData/Lu Liu Cong.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);

		Boolean expected = true;
		Boolean bibliography = tp.bibliographyExistence();

		assertEquals(expected, bibliography);
	}

	@org.junit.Test
	public void numberOfWords_number_returnsNumber() {
		File selectedFile = new File("src/testData/probaszavak.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);

		int number = 3;
		int numOfWords = (int) tp.numberOfWords();
		assertEquals(number, numOfWords);
	}
	
	@org.junit.Test
	public void getTheAverageRowFromParagraphs_avgNumOfRow_returnsAvg() {
		File selectedFile = new File("src/testData/Lu Liu Cong.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);

		int avgNum = 9;
		int numOfWords = (int) tp.getTheAverageRowFromParagraphs();
		
		assertEquals(avgNum, numOfWords);
	}
	
	@org.junit.Test
	public void isStopWord_stopWord_returnsTrue() {
		String word = "one";
		Boolean answer = common.StopWords.isStopWord(word);
		assertEquals(answer, true);
	}
	
	@org.junit.Test
	public void isStopWord_notStopWord_returnsFalse() {
		String word = "mice";
		Boolean answer = common.StopWords.isStopWord(word);
		assertEquals(answer, false);
	}
	
	@org.junit.Test
	public void getTheMostUsedFont_mostUsedFont_returnsMostUsedFont() {
		File selectedFile = new File("src/testData/test.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);
		
		float fontSize = tp.getTheMostUsedFont();
		float myTestFontSize = (float) 9.0;
		boolean f = (fontSize == myTestFontSize );
		assertEquals(f, true);
	}
	
	@org.junit.Test
	public void getFontFamily_fontFamily_returnsFontFamily() {
		File selectedFile = new File("src/testData/test.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);
		String fontFamily = tp.getFontFamily("+HelveticaNeue-Light+,8.0 +Owner’s Manual+");
		String myTestFontFamily = "HelveticaNeue-Light";
		assertEquals(fontFamily, myTestFontFamily);
	}
	
	@org.junit.Test
	public void simplifyWord_string_returnsSimplifyString() {
		String myString = common.Tools.simplifyWord("(hello?!");
		String myTestString = "hello";
		
		boolean f = (myString.equals(myTestString) );
		assertEquals(f, true);
	}
	
	@org.junit.Test
	public void countWordOccurence_number_returnsNumber() {
		File selectedFile = new File("src/testData/test.pdf");
		TextProcessor tp = new TextProcessor(selectedFile);
		tp.countWordOccurence("Hello! My name is Hello!");
		
		Integer myNumber = PDFContainer.wordsOccurence.get("hello");
		
		System.out.println("myNumber " + myNumber );
		boolean f = (myNumber == 2);
		assertEquals(f, true);
	}	
	
}

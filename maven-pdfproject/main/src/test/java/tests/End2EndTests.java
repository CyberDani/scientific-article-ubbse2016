package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoIterable;

import backend.model.PDF;
import backend.repository.DAOFactory;
import backend.repository.PDFDAO;
import backend.repository.jdbc.JdbcPDFDAO;
import common.Scientific;


public class End2EndTests {
	
	@org.junit.Test
	public void Connection_validConnection_connectionSuccessfull() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		String expectedConnectionPort = "localhost:27017";
		String actualPort = "";
		
		try {
			actualPort = mongoClient.getAddress().toString();
		} catch (Exception e) {
			fail("Connection_validConnection_connectionSuccessfull fail");
		}
		
		mongoClient.close();
		assertEquals(expectedConnectionPort, actualPort);
	}
	
	@org.junit.Test
	public void Connection_dataExists_returnsTrue() {
		frontend.app.Main.initializeData();
		JdbcPDFDAO jdao = new JdbcPDFDAO();
		List<PDF> pdfs = jdao.getAllPDFs();
		jdao.getCM().closeConnection();
		assertTrue(pdfs.size() > 0);
	}
	
	@org.junit.Test
	public void createConnection_existPDFdata_returnsConnection() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		ListDatabasesIterable<Document> a = mongoClient.listDatabases();
		boolean find = false;
		Iterator<Document> helper = a.iterator();
		while(helper.hasNext()){
			Set<Entry<String, Object>> set= helper.next().entrySet();
			String[] m = set.toString().split(",");
			m[0] = m[0].substring(1);
			String z = m[0].split("=")[1];
			if(z.equals("PDFdata")){
				find = true;
				break;
			}
		}
		
		mongoClient.close();
		assertTrue(find);
	}
	
	@org.junit.Test
	public void getAllPDFs_numberOfAllRecords_returnsList() {
		frontend.app.Main.initializeData();
		PDFDAO pdfDAO = DAOFactory.getInstance().getPDFDAO();
		List<PDF> list = pdfDAO.getAllPDFs();
		int expected = 100;
		pdfDAO.getCM().closeConnection();
		assertTrue(list.size() == expected);
	}
	
	@org.junit.Test
	public void getAllPDFs_existsCollection_returnsList() {
		boolean find = false;
		frontend.app.Main.initializeData();
		PDFDAO pdfDAO = DAOFactory.getInstance().getPDFDAO();
		MongoIterable<String> list = pdfDAO.getCM().getDatabase().listCollectionNames();
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String z = iterator.next().toString();
			if (z.equals("LearningData")) {
				find = true;
				break;
			}
		}
		pdfDAO.getCM().closeConnection();
		assertTrue(find);
	}
	
	// to a pdf from db we get the correct fields
	@org.junit.Test
	public void getAllPDFs_persistencyCheck_returnsList() {
		boolean ok = false;
		
		frontend.app.Main.initializeData();
		PDFDAO pdfDAO = DAOFactory.getInstance().getPDFDAO();
		List<PDF> list = pdfDAO.getAllPDFs();
		int n = list.size();
		for(int i = 0; i< n; ++i){
			if(list.get(i).getPath().endsWith("testData\\testforjunit2.pdf")){
				PDF data = list.get(i);
				if(data.isScientific()){
					if(data.getFileSize() == 219){
						if(data.getImgNum() ==0){
							if(data.getAvgRowInParagraph() == 50){
								if(data.getBibliography()){
									if(data.getFontSize() == 9){
										if(data.getWordsRow() > 6.0 && data.getWordsRow()<7.0){
											if(data.getPagesNr()==15){
												HashMap<String, Integer> hm = data.getWords();
												if(hm.get("iteration")==20 && hm.get("point")==19){
													ok = true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
				break;
			}
		}
		
		pdfDAO.getCM().closeConnection();
		
		assertTrue(ok);
	}
	
	@org.junit.Test
	public void insertPDF_afterAnInsert_insertToDB() {
		frontend.app.Main.initializeData();
		PDFDAO pdfDAO = DAOFactory.getInstance().getPDFDAO();
		List<PDF> list = pdfDAO.getAllPDFs();
		int beforeInsert = list.size();
		int expected = beforeInsert + 1;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("point", 8);
		hm.put("iteration", 1);
		hm.put("hardwork", 3000);
		hm.put("sleep", 1);
		PDF pdf = new PDF("fake.pdf", hm, 3, 12.2, 12, 8, 123, true, 123, Scientific.SCIENTIFIC);
		try {
			pdfDAO.insertPDF("LearningData", pdf);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		List<PDF> list2 = pdfDAO.getAllPDFs();
		
		pdfDAO.getCM().closeConnection();
		assertTrue(list2.size() == expected);
	}
	
	@org.junit.Test
	public void insertPDF_afterAnInsertCorrect_insertToDB() {
		frontend.app.Main.initializeData();
		PDFDAO pdfDAO = DAOFactory.getInstance().getPDFDAO();
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("point", 8);
		hm.put("iteration", 1);
		hm.put("hardwork", 3000);
		hm.put("sleep", 1);
		
		/*
		 * 
		 * String path, HashMap<String, Integer> words, int pagesNr, double wordsRow, 
			float fontSize,  int imgNum, int averageRowInParagraph, 
			Boolean bibliography, long fileSize , Scientific sc)
		 * */
		
		PDF pdf = new PDF("fakeInsert.pdf", hm, 3, 12.2, 12, 8, 123, true, 123, Scientific.SCIENTIFIC);
		try {
			pdfDAO.insertPDF("LearningData", pdf);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		boolean ok = false;
		List<PDF> list = pdfDAO.getAllPDFs();
		int n = list.size();
		for(int i = 0; i< n; ++i){
			if(list.get(i).getPath().equals("fakeInsert.pdf")) {
				PDF data = list.get(i);
				if(data.isScientific()){
					if(data.getFileSize() == 123){
						if(data.getImgNum() == 8){
							if(data.getAvgRowInParagraph() == 123){
								if(data.getBibliography()){
									if(data.getFontSize() == 12){
										if(data.getWordsRow() == 12.2){
											if(data.getPagesNr()==3){
												HashMap<String, Integer> hm2 = data.getWords();
												if(hm2.get("iteration") == 1 && hm2.get("point") == 8){
													ok = true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
				break;
			}
		}
	
		pdfDAO.getCM().closeConnection();
		assertTrue(ok);
	}
	
	@org.junit.Test
	public void closeConnection_afterClose_connectionIsNull() {
		frontend.app.Main.initializeData();
		JdbcPDFDAO jdao = new JdbcPDFDAO();
		jdao.getCM().closeConnection();
		
		try {
			String z =jdao.getCM().getMongoClient().getAddress().toString();
			fail("Connection still exists");
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	@org.junit.Test
	public void insertPDF_numberOfPageGreaterThan40_notInsertToDB() {
		frontend.app.Main.initializeData();
		
		PDFDAO pdfDAO = null;
		try {
			pdfDAO = DAOFactory.getInstance().getPDFDAO();
		} catch (Exception e1) {
			fail("insertPDF_numberOfPageGreaterThan40_notInsertToDB fail");
		}
		
		List<PDF> list = pdfDAO.getAllPDFs();
		int beforeInsert = list.size();
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("point", 8);
		hm.put("iteration", 1);
		hm.put("hardwork", 3000);
		hm.put("sleep", 1);
		PDF pdf = new PDF("fake.pdf", hm, 41, 12.2, 12, 8, 123, true, 123, Scientific.SCIENTIFIC);
		try {
			pdfDAO.insertPDF("LearningData", pdf);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		List<PDF> list2 = pdfDAO.getAllPDFs();
		
		pdfDAO.getCM().closeConnection();
		assertTrue(list2.size() == beforeInsert);
	}
}

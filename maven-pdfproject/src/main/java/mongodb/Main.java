package mongodb;

import java.lang.reflect.Field;

import app.GUI;
import common.ConnectionContainer;
import common.PDFContainer;

public class Main {

	public static void main(String[] args) {
		
		// PDF osztaly attributimainak nevenek es tipusanak meghatarozasa(reflection-hez)
		Field[] fields = PDF.class.getDeclaredFields();
		PDFContainer.attrNo = fields.length;
		PDFContainer.PDFAttrNames = new String[PDFContainer.attrNo];
		PDFContainer.PDFAttrTypes = new Class[PDFContainer.attrNo];
		
		for(int i = 0;i<PDFContainer.attrNo;i++) {
			fields[i].setAccessible(true);
			PDFContainer.PDFAttrNames[i] = fields[i].getName();
			PDFContainer.PDFAttrTypes[i] = fields[i].getType();
		}
		
		
		// Kapcsolat kezdemenyezese
		final MongoDBJDBC db = new MongoDBJDBC("PDFdata", "localhost", 27017);	
		db.createConnection();
		ConnectionContainer.dm = new DataManager(db.getDatabase());
		
		
		// Alkalmazas bezarasa
		Runtime.getRuntime().addShutdownHook(new Thread() 
		{            
			public void run() {                
				db.closeConnection();
			}        
		});
		
		
		//GUI
		new GUI();

	}
}

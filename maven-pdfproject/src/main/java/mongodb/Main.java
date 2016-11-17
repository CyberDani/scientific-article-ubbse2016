package mongodb;

import app.GUI;
import common.ConnectionContainer;

public class Main {

	public static void main(String[] args) {
		final MongoDBJDBC db = new MongoDBJDBC("PDFdata", "localhost", 27017);
		
		
		db.createConnection();
		ConnectionContainer.dm = new DataManager(db.getDatabase());
		Runtime.getRuntime().addShutdownHook(new Thread() 
		{            
			public void run() {                
				//System.out.println("Exited");
				db.closeConnection();
			}        
		});
		
		new GUI();

	}
}

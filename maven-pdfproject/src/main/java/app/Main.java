package app;

import java.lang.reflect.Field;

import common.ConnectionContainer;
import common.PDFContainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mongodb.DataManager;
import mongodb.MongoDBJDBC;
import mongodb.PDF;

public class Main extends Application {

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
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../gui/ScientificArticleApp2.fxml"));
		AnchorPane myApp = (AnchorPane) loader.load();
		Scene scene = new Scene(myApp);
		primaryStage.setScene(scene);
		primaryStage.show();
		scene.getStylesheets().add(getClass().getResource("../gui/styles.css").toExternalForm());
		
	}
}

package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import app.Main;
import common.PDFContainer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mongodb.PDF;
import weka.LearningDataSet;

public class LearningFXController {

	@FXML
	private Button loadFromDbButton;
	
	@FXML
	private Button loadFromFileButton;
	
	@FXML
	private Button saveTrainingSetButton;
	
	@FXML
	private Button loadOtherSceneButton;
	
	private boolean isDataSetLoaded=false;
	
	@FXML
	public void loadDataFromDB(){
			PDF dbData[] = common.ConnectionContainer.dm.findAll(); 
			PDFContainer.lds = new LearningDataSet();
			PDFContainer.lds.addAllPDF(dbData, true);
			PDFContainer.lds.write();
			isDataSetLoaded=true;
	}
	
	@FXML
	public void loadDataFromFile(){
		Stage stage = (Stage) loadFromFileButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Attribute Relational File Format", "arff");
        fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Training Set File");
		File selectedFile= fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			PDFContainer.lds = new LearningDataSet(selectedFile.getAbsolutePath());
			PDFContainer.lds.write();
			isDataSetLoaded=true;
		}			
	}
	
	public void saveFile(File file){
		FileWriter fileWriter = null;
        try {
        	 fileWriter = new FileWriter(file);
			 fileWriter.write(PDFContainer.lds.toString());
			 fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}     
	}
	
	@FXML
	public void saveTrainingSet(){
		Stage stage = (Stage) saveTrainingSetButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Attribute Relational File Format", "arff");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save training set");
		File file = fileChooser.showSaveDialog(stage);
		
		
		if(file!=null){
			saveFile(file);
		}
	}
	
	@FXML
	public void changeScene(){
	  if(isDataSetLoaded==true){
			 Stage stage= (Stage) saveTrainingSetButton.getScene().getWindow();
			 FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(Main.class.getResource("../gui/ScientificArticleApp.fxml"));
			 AnchorPane myApp;
			try {
				 myApp = (AnchorPane) loader.load();
				 Scene scene = new Scene(myApp);
				 stage.setScene(scene);
				 stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Data set is not loaded");
		}
	}
	
}

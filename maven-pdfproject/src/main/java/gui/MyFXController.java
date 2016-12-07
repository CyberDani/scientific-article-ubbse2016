package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import app.TextProcessor;
import common.PDFContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mongodb.PDF;
import weka.LearningDataSet;

public class MyFXController {
	
	@FXML
	private Button loadPdfButton;

	@FXML
	private Button loadFromDbButton;
	
	@FXML
	private Button loadFromFileButton;
	
	@FXML
	private Button saveTrainingSetButton;
	
	@FXML 
	private Label pageNumber;

	@FXML
	private Label pageNumberValue;
	
	@FXML 
	private Label avgWords;

	@FXML
	private Label avgWordsValue;
	
	@FXML 
	private Label mostUsedFont;

	@FXML
	private Label mostUsedFontValue;
	
	@FXML 
	private Label mostUsedTitleFont;

	@FXML
	private Label mostUsedTitleFontValue;
	
	@FXML 
	private Label bibliography;

	@FXML
	private Label bibliographyValue;
	
	private void setLabels(TextProcessor tp){
		PDF myPDF=tp.getPDF();
		
		pageNumber.setVisible(true);
		pageNumberValue.setText(Integer.toString(myPDF.getPagesNr()));
		pageNumberValue.setVisible(true);
		
		avgWords.setVisible(true);
		avgWordsValue.setText(Double.toString(myPDF.getWordsRow()));
		avgWordsValue.setVisible(true);
		
		mostUsedFont.setVisible(true);
		mostUsedFontValue.setText(myPDF.getFontSize());
		mostUsedFontValue.setVisible(true);
		
		mostUsedTitleFont.setVisible(true);
		mostUsedTitleFontValue.setText(myPDF.getMostUsedTitleFont());
		mostUsedTitleFontValue.setVisible(true);
		
		bibliography.setVisible(true);
		bibliographyValue.setText(Boolean.toString(myPDF.getBibliography()));
		bibliographyValue.setVisible(true);
	}
	
	private void openFile(){
		Stage stage = (Stage) loadPdfButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		File selectedFile= fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			TextProcessor tp=new TextProcessor(selectedFile);
			setLabels(tp);
		}		
	}
	
	@FXML
	public void loadPdf() {
		openFile();
	}
	
	@FXML
	public void loadDataFromDB(){
			PDF dbData[] = common.ConnectionContainer.dm.findAll(); 
			PDFContainer.lds = new LearningDataSet();
			PDFContainer.lds.addAllPDF(dbData, true);
			PDFContainer.lds.write();
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
	
	
	
	

}

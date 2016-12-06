package gui;

import java.io.File;

import app.TextProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mongodb.PDF;

public class MyFXController {
	
	@FXML
	private Button loadPdfButton;

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

}

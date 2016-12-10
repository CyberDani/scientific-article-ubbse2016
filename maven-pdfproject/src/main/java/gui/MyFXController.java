package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import app.TextProcessor;
import common.PDFContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mongodb.PDF;
import weka.LearningDataSet;

public class MyFXController {
	
	@FXML
	private Text result;
	
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
	private Label avgRowParagraph;
	
	@FXML
	private Label avgRowParagraphValue;
	
	@FXML 
	private Label mostUsedFont;

	@FXML
	private Label mostUsedFontValue;
	
	@FXML
	private Label numOfImg;
	
	@FXML
	private Label numOfImgValue;
	
	@FXML 
	private Label bibliography;

	@FXML
	private Label bibliographyValue;
	
	private void setLabels(TextProcessor tp){
		PDF myPDF=tp.getPDF();
		
		result.setVisible(true);
		
		pageNumber.setVisible(true);
		pageNumberValue.setText(Integer.toString(myPDF.getPagesNr()));
		pageNumberValue.setVisible(true);
		
		avgWords.setVisible(true);
		avgWordsValue.setText(Double.toString(myPDF.getWordsRow()));
		avgWordsValue.setVisible(true);
		
		avgRowParagraph.setVisible(true);
		avgRowParagraphValue.setText(Integer.toString(myPDF.getAvgRowInParagraph()));
		avgRowParagraphValue.setVisible(true);
		
		mostUsedFont.setVisible(true);
		mostUsedFontValue.setText(myPDF.getFontSize());
		mostUsedFontValue.setVisible(true);
		
		numOfImg.setVisible(true);
		numOfImgValue.setText(Integer.toString(myPDF.getImgNum()));
		numOfImgValue.setVisible(true);
		
		bibliography.setVisible(true);
		bibliographyValue.setText(Boolean.toString(myPDF.getBibliography()));
		bibliographyValue.setVisible(true);
	}
		
	@FXML
	public void loadPdf() {
		Stage stage = (Stage) loadPdfButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		File selectedFile= fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			TextProcessor tp=new TextProcessor(selectedFile);
			setLabels(tp);
		}		
	}
	
}

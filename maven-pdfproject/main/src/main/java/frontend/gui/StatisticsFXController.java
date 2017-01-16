package frontend.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import backend.model.PDF;
import common.PDFContainer;
import frontend.app.Main;
import frontend.app.TextProcessor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StatisticsFXController {
	
	TextProcessor tp;
	PDF[] pdfs;
	
	@FXML
	private Button saveStat;
	
	@FXML
	private Label pdfNameValue;
	
	@FXML
	private Label isScientificValue;
	
	@FXML
	private Label avgWordsValue;
	
	@FXML
	private Label pageNumberValue;
	
	@FXML
	private Label mostUsedFontValue;
	
	@FXML
	private Label avgRowParagraphValue;
	
	@FXML
	private Label bibliographyValue;
	
	@FXML
	private Label numOfImgValue;
	
	@FXML
	private Label pdfComboText;
	
	@FXML
	private Button backButton;
	
	@FXML
	private ComboBox pdfCombo;
	
	/**
	 * Initializes the page
	 * @param tp - needs the processed PDF
	 */
	void initializePage(TextProcessor tp){
		this.tp=tp;
		setLabels(-1);
	}
	
	void initializePage(PDF[] pdfs){
		this.pdfs=pdfs;
		
		for(PDF pdf:pdfs){
			String[] pdfName = pdf.getPath().split("\\\\");
			pdfCombo.getItems().add(pdfName[pdfName.length-1]);
		}
		pdfComboText.setVisible(true);
		pdfCombo.setVisible(true);
	}
	
	/**
	 * Builds the statistics into a string
	 * @return all statistics in a string
	 * @param if there are multiple PDF's you need to select one
	 */
	private String buildStatisticsString(int multiple){
		
		PDF myPDF;
		if(multiple==-1){
		  myPDF=tp.getPDF();
		}else{
		  myPDF=pdfs[multiple];
		}
		
		String path[]=myPDF.getPath().split("\\\\");
		String statistics="PDF name:"+path[path.length-1]+System.getProperty("line.separator")
						+"Is scientific:"+System.getProperty("line.separator")
						+"Page number:"+myPDF.getPagesNr()+System.getProperty("line.separator")
						+"Average words in a row:"+myPDF.getWordsRow()+System.getProperty("line.separator")
						+"Average row/paragraph:"+myPDF.getAvgRowInParagraph()+System.getProperty("line.separator")
						+"Most used font size:"+myPDF.getFontSize()+System.getProperty("line.separator")
						+"Number of images:"+myPDF.getImgNum()+System.getProperty("line.separator")
						+"Bibliography available:"+myPDF.getBibliography();
		
		return statistics;
	}
	
	/**
	 * Save statistics in file
	 */
	@FXML
	public void saveStatistics(){
		 Stage stage= (Stage) saveStat.getScene().getWindow();
		 FileChooser fileChooser = new FileChooser();
		 
		 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		 fileChooser.getExtensionFilters().add(extFilter);
		 File file = fileChooser.showSaveDialog(stage);
		 
		 if(file != null){
			 try {
				String data;
				if(pdfCombo.isVisible())
				  data=buildStatisticsString(pdfCombo.getSelectionModel().getSelectedIndex());
				else
				  data=buildStatisticsString(-1);
				
	            FileWriter fileWriter = null;
	            fileWriter = new FileWriter(file);
	            fileWriter.write(data);
	            fileWriter.close();
	        } catch (IOException ex) {
	        	//logger here later
	      }
		}
	}
	
	/**
	 * Sets the labels visible
	 */
	private void setLabelsVisible(){
		pdfNameValue.setVisible(true);
		isScientificValue.setVisible(true);
		pageNumberValue.setVisible(true);
		avgWordsValue.setVisible(true);
		avgRowParagraphValue.setVisible(true);
		mostUsedFontValue.setVisible(true);
		numOfImgValue.setVisible(true);
		bibliographyValue.setVisible(true);
	}
	
	/**
	 * Set labels on GUI with the corresponding value
	 * @param if multiple PDF s are loaded we need the index of the PDF
	 */
	private void setLabels(int multiple){
		
		PDF myPDF;
		if(multiple==-1){
			myPDF=tp.getPDF();
		}else{
			myPDF=pdfs[multiple];
		}
		
		String[] pdfName = myPDF.getPath().split("\\\\");
		pdfNameValue.setText(pdfName[pdfName.length-1]);
		
		String res = PDFContainer.dlp.predict(myPDF);
		if(res!=null){
			if(res.equals("-1")){
				isScientificValue.setText("NON-SCIENTIFIC");
			}else{
				isScientificValue.setText("SCIENTIFIC");
			}
		}
		
		pageNumberValue.setText(Integer.toString(myPDF.getPagesNr()));
		avgWordsValue.setText(Double.toString(myPDF.getWordsRow()));
		avgRowParagraphValue.setText(Integer.toString(myPDF.getAvgRowInParagraph()));
		mostUsedFontValue.setText(Double.toString(myPDF.getFontSize()));
		numOfImgValue.setText(Integer.toString(myPDF.getImgNum()));
		bibliographyValue.setText(Boolean.toString(myPDF.getBibliography()));		
		setLabelsVisible();
		
	}
	
	/**
	 * Sets the selected PDF data to GUI
	 */
	@FXML
	private void setSelectedPDF(){
		setLabels(pdfCombo.getSelectionModel().getSelectedIndex());
	}
	
	/**
	 * Sets the mainpage scene
	 */
	@FXML
	private void backToMainPage(){
		 Stage stage= (Stage) backButton.getScene().getWindow();
		 FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(Main.class.getResource("../gui/ScientificArticleApp2.fxml"));
		 AnchorPane myApp;
		try {
			 myApp = (AnchorPane) loader.load();
			 Scene scene = new Scene(myApp);
			 scene.getStylesheets().add(getClass().getResource("../gui/styles.css").toExternalForm());
			 stage.setScene(scene);
			 stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

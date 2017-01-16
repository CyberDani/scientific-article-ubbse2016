package frontend.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import backend.model.PDF;
import backend.weka.DataLearnerPredictor;
import common.LearningAlgorithm;
import common.PDFContainer;
import common.Scientific;
import frontend.app.Main;
import frontend.app.TextProcessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MyFXController {
	
	private TextProcessor tp;
	
	@FXML
	private Text result;
	
	@FXML
	private Button loadPdfButton;
	
	@FXML
	private Button loadDirPDFs;
	
	@FXML
	private ComboBox<String> dataStructCombo;
	
	@FXML
	private ComboBox<String> algorithmCombo;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button saveStat;
	
	@FXML
	private Button showStat;
	
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
	
	@FXML 
	private Label accuracyLabel;
	
	@FXML 
	private Label scientificLabel;
	
	/**
	 * Load the base GUI
	 */
	@FXML
	public void initialize(){
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Decision tree",
			        "Decision tree with linear regression"
			    );
		
		dataStructCombo.setItems(options);
	}
	
	
	/**
	 * Loads the window where you train your data
	 */
	@FXML
	public void backToTraining(){
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
	
	
	/**
	 * Sets the page where are statistics about the processed PDF
	 */
	@FXML
	public void showStaticsPage(){
		Stage stage= (Stage) showStat.getScene().getWindow();
		 FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(Main.class.getResource("../gui/ScientificArticleApp3.fxml"));
		 AnchorPane myApp;
		try {
			 myApp = (AnchorPane) loader.load();
			 StatisticsFXController statisticsCont=loader.<StatisticsFXController>getController();
			 statisticsCont.initializePage(tp);
			 Scene scene = new Scene(myApp);
			 stage.setScene(scene);
			 stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Sets the training algorithms combobox
	 */
	@FXML
	public void dataStructSelected(){
		int ind = dataStructCombo.getSelectionModel().getSelectedIndex();
		ObservableList<String> options = null;
		
		switch (ind) {
		case 0:
				options = FXCollections.observableArrayList(
			        "J48(C4.5)"
			    );
			break;
		case 1:
			options = FXCollections.observableArrayList(
				"M5P(M5 Base)"
		    );
			break;
		case 2:
			options = FXCollections.observableArrayList(
		        "Nothing yet"
		    );
			break;
		case 3:
			options = FXCollections.observableArrayList(
		        "Nothing yet"
		    );
			break;
		default:
			break;
		}
		
		algorithmCombo.setItems(options);
	}
	
	/**
	 * Train button action
	 */
	@FXML
	public void trainPushed(){
		String alg = algorithmCombo.getValue();

		if(alg == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning dialog");
			alert.setHeaderText("You have not choosen an algorithm.");
			alert.setContentText("Please choose an existing algorithm from the combobox.");
			alert.showAndWait();
		}else if(alg.equals("J48(C4.5)")){
			
			PDFContainer.dlp = new DataLearnerPredictor(PDFContainer.lds);
			PDFContainer.dlp.setAlgorithm(LearningAlgorithm.DecisionTree_J48);
			PDFContainer.dlp.train();
		}else if(alg.equals("M5P(M5 Base)")){	
			
			PDFContainer.dlp = new DataLearnerPredictor(PDFContainer.lds);
			PDFContainer.dlp.setAlgorithm(LearningAlgorithm.DecisionTreeLinRegression_M5P);
			PDFContainer.dlp.train();
		}
	}
	
	
	/**
	 * Calls the cross validation
	 */
	@FXML
	public void crossValidation(){
		if(PDFContainer.dlp != null){
			double val = PDFContainer.dlp.crossValidation();
			accuracyLabel.setText(String.format("%.3f", val) + " %");
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning dialog");
			alert.setHeaderText("You have not train your dataset.");
			alert.setContentText("Please choose an existing algorithm from the combobox "
					+ "and train your existing dataset.");
			alert.showAndWait();
		}
		
	}
	
	/**
	 * Alert the user that he don't trained the dataset
	 */
	public void alertMessage(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning dialog");
		alert.setHeaderText("You have not train your dataset.");
		alert.setContentText("Please choose an existing algorithm from the combobox "
				+ "and train your existing dataset.");
		alert.showAndWait();
	}
	
	/**
	 * Loads pdf to decide if it is scientific or not
	 */
	@FXML
	public void loadPdf() {
		if(PDFContainer.dlp != null){
			Stage stage = (Stage) loadPdfButton.getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open File");
			File selectedFile= fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				
				tp = new TextProcessor(selectedFile,Scientific.UNKNOWN);
				
				PDF pdf = tp.getPDF();
				
				String res = null;
				try {
					res = PDFContainer.dlp.predict(pdf);
				} catch (Exception e) {}
				
				if(res!=null){
					if(res.equals("-1")){
						scientificLabel.setText("NON-SCIENTIFIC");
					}else{
						scientificLabel.setText("SCIENTIFIC");
					}
				}
				
				//setLabels(tp);
				showStat.setVisible(true);
			}		
		}else{
			alertMessage();
		}
	}
	
	/**
	 * Filter to find PDF's
	 */
	public File[] pdfFinder(File selectedDirectory) {
		FilenameFilter fileNameFilter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".pdf");
			}

		};

		return selectedDirectory.listFiles(fileNameFilter);
	}
	/**
	 * Sets the page where are statistics about the processed PDF's
	 */	
	public void changeToStatistics(PDF[] pdfsProcessed){
			Stage stage= (Stage) showStat.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../gui/ScientificArticleApp3.fxml"));
			AnchorPane myApp;
			
			try {
				 myApp = (AnchorPane) loader.load();
				 StatisticsFXController statisticsCont=loader.<StatisticsFXController>getController();
				 statisticsCont.initializePage(pdfsProcessed);
				 Scene scene = new Scene(myApp);
				 stage.setScene(scene);
				 stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}	

	/**
	 * Loads a directory of pdf's to decide if it is scientific or not
	 */
	@FXML
	public void loadDirOfPDFs(){
		if(PDFContainer.dlp != null){
			Stage stage = (Stage) loadDirPDFs.getScene().getWindow();
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Load Directory of PDF's");
			File selectedDirectory = chooser.showDialog(stage);
			if(selectedDirectory!=null){
				
				File [] pdfs = pdfFinder(selectedDirectory);
				Scientific sc;
				sc = Scientific.UNKNOWN;
				PDF[] pdfsProcessed;
				pdfsProcessed=new PDF[pdfs.length];

				for(int i=0;i<pdfs.length;i++){
					String scientificValue="";
					System.out.println("Processed PDF:" + pdfs[i]);
					PDF currentPDF=new TextProcessor(pdfs[i],sc).getPDF();
					pdfsProcessed[i]=currentPDF;
				}
				
				changeToStatistics(pdfsProcessed);
				
			}
		}else{
			alertMessage();
		}
	}
	
}

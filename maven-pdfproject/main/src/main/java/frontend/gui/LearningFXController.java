package frontend.gui;

import java.awt.Component;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.drew.metadata.Directory;
import com.graphbuilder.struc.LinkedList;

import backend.model.PDF;
import backend.repository.DAOFactory;
import backend.weka.LearningDataSet;
import common.PDFContainer;
import common.Scientific;
import common.Settings;
import common.hashMapSort;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.main.Controller;
import edu.uci.ics.crawler4j.main.MyCrawler;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import frontend.app.Main;
import frontend.app.TextProcessor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LearningFXController {

	@FXML
	private Button loadFromDbButton;

	@FXML
	private CheckBox scientificCheck;

	@FXML
	private Button loadFromFileButton;

	@FXML
	private Button saveTrainingSetButton;

	@FXML
	private Button loadOtherSceneButton;

	@FXML 
	private Button loadPdfToDbButton;

	@FXML
	private Button loadDirOfPdfButton;

	@FXML 
	private Button setSeedsFileButton;

	@FXML 
	private Button setStorageFolderButton;

	@FXML 
	private Button runCrawlerButton;

	@FXML 
	private ChoiceBox<String> choiceB;

	@FXML
	private Pane crawlerPane;

	@FXML 
	private Pane trainingPane;

	@FXML
	private Pane loadDataPane;

	private boolean isDataSetLoaded = false;
	private String storageFolder;
	private String seedsFile;
	private Boolean seedsFileSelected = false;
	private Boolean storageFolderSelected = false;
	
	/**
	 * Load the base GUI
	 */
	@FXML
	public void initialize(){
		choiceB.getItems().addAll("Crawler", "Learning");
		choiceB.getSelectionModel().select("Crawler");
		choiceB.valueProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch(newValue){
					case "Crawler": {
						crawlerPane.setVisible(true);
						trainingPane.setVisible(false);
						loadDataPane.setVisible(false);
						break;
					}
					case "Learning":{
						crawlerPane.setVisible(false);
						trainingPane.setVisible(true);
						loadDataPane.setVisible(true);
						break;
					}
				}
			}    
		});
	}
	

	/**
	 * Sets the storage folder of crawler(the folder where the downloaded PDF's are)
	 */
	@FXML 
	public void setStorageFolder() {
		Stage stage = (Stage) setStorageFolderButton.getScene().getWindow();
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Set Storage Folder");
		File selectedDirectory = chooser.showDialog(stage);
		if (selectedDirectory != null) {
			storageFolder = selectedDirectory.getPath();
			storageFolderSelected = true;
			if (seedsFileSelected) {
				runCrawlerButton.setDisable(false);
			}
		}
		else {
			storageFolderSelected = false;
			runCrawlerButton.setDisable(true);		
		}
	}

	
	/**
	 * Sets the file of links for the crawler
	 */
	@FXML 
	public void setSeedsFile()  {
		Stage stage = (Stage) setSeedsFileButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT FILES", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Set Seeds File");
		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			seedsFile = selectedFile.getPath();
			seedsFileSelected = true;
			if (storageFolderSelected) {
				runCrawlerButton.setDisable(false);
			}
		}
		else {
			seedsFileSelected = false;
			runCrawlerButton.setDisable(true);
		}
	}

	
	/**
	 * Runs the crawler
	 */
	@FXML 
	public void runCrawler() {
		String crawlStorageFolder = "/data/crawl/root";
		int numberOfCrawlers = 7;
	
		Controller controller = new Controller(crawlStorageFolder, numberOfCrawlers, storageFolder, seedsFile);
		try {
			controller.runCrawler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	
	/**
	 * Loads the training set from database
	 */
	@FXML
	public void loadDataFromDB() {
		PDFContainer.dbData = new ArrayList<PDF>();
		PDFContainer.dbData = DAOFactory.getInstance().getPDFDAO().getAllPDFs();
		
		NavigableMap<String, Double> pdfScientificTFIDF = new TreeMap<String, Double>();
		NavigableMap<String, Double> pdfNotScientificTFIDF = new TreeMap<String, Double>();
		
		NavigableMap<String, Double> pdfDataOfWords = new TreeMap<String, Double>();
		
		List<Entry<String,Double>> sortedPDFScientificTFIDF, sortedPDFNotScientificTFIDF;
		
		
		String key, key2 = null;
		@SuppressWarnings("unused")
		Integer numOfWordsInCurrentPDF, contains=0;
		@SuppressWarnings("unused")
		double value, value2, TFIDF, hashMapValue;
		boolean isScientific;
		
		for(int i=0; i<PDFContainer.dbData.size(); i++) {

			HashMap<String, Integer> words = PDFContainer.dbData.get(i).getWords(); // getting words from 1 PDF
			isScientific = PDFContainer.dbData.get(i).isScientific();
			for(Entry<String, Integer> entry : words.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				contains = 0;
				
				for(int j=0; j<PDFContainer.dbData.size(); j++) {
					
					// Preventing the different type (scientific, non scientific) pdf analysation.
					// We only need the same contra same pdf type.
					if(isScientific != PDFContainer.dbData.get(j).isScientific()){
						continue;
					}
					
					if(j != i){					// searching the word appearance from only other than the current pdf
						HashMap<String, Integer> words2 = PDFContainer.dbData.get(j).getWords(); // getting words from 1 PDF

						for(Entry<String, Integer> entry2 : words2.entrySet()) {
							key2 = entry2.getKey();
							if(key.equals(key2)){
								contains++;
								break;
							}
						}
					}
				}
				
				if(isScientific && (pdfScientificTFIDF.isEmpty() || !pdfScientificTFIDF.containsKey(key2))) {
					TFIDF = value * Math.log(PDFContainer.dbData.size()  /  (1+contains));	// addig 1 to the divisor will prevent division by zero
					pdfScientificTFIDF.put(key, TFIDF);
				}
				else if(!isScientific && (pdfNotScientificTFIDF.isEmpty() || !pdfNotScientificTFIDF.containsKey(key2))) {
					TFIDF = value * Math.log(PDFContainer.dbData.size()  /  (1+contains));	// addig 1 to the divisor will prevent division by zero
					pdfNotScientificTFIDF.put(key, TFIDF);
				}
			}
		}
		
		sortedPDFScientificTFIDF = hashMapSort.entriesSortByValues(pdfScientificTFIDF);
		sortedPDFNotScientificTFIDF = hashMapSort.entriesSortByValues(pdfNotScientificTFIDF);
		
		int sc = sortedPDFScientificTFIDF.size()-1;
		int nonSc = sortedPDFNotScientificTFIDF.size()-1;
		
		for(int i=0; pdfDataOfWords.size() < Settings.selectedWordsNr; i++) {	
			
			if(sortedPDFScientificTFIDF.size() > i){
				key = sortedPDFScientificTFIDF.get(i).getKey();
				value = sortedPDFScientificTFIDF.get(i).getValue();
				pdfDataOfWords.put(key, value);
			}
			
			if(pdfDataOfWords.size() != Settings.selectedWordsNr){
				
				if(sortedPDFNotScientificTFIDF.size() > i){
					key2 = sortedPDFNotScientificTFIDF.get(i).getKey();
					value2 = sortedPDFNotScientificTFIDF.get(i).getValue();
					pdfDataOfWords.put(key2, value2);
				}
			}
		}
		
		List<String> pdfWordList = new Vector<String>();
		
		for(String word: pdfDataOfWords.keySet()){
			pdfWordList.add(word);
		}
		
		PDFContainer.lds = new LearningDataSet(pdfWordList);

		PDFContainer.lds.addAllPDF(PDFContainer.dbData);
		PDFContainer.lds.write();
		isDataSetLoaded = true;
	}

	
	/**
	 * Loads the training set from file
	 */
	@FXML
	public void loadDataFromFile(){
		Stage stage = (Stage) loadFromFileButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Attribute Relational File Format", "*.arff");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Training Set File");
		File selectedFile= fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {

			
			PDFContainer.lds = new LearningDataSet(selectedFile.getAbsolutePath());

			//PDFContainer.lds.buildFromFile(selectedFile.getAbsolutePath());
			PDFContainer.lds.write();
			isDataSetLoaded=true;
		}			
	}
	
	
	/**
	 * 
	 * @param file
	 */
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

	
	/**
	 * Saves the training set to a file
	 */
	@FXML
	public void saveTrainingSet() {
		Stage stage = (Stage) saveTrainingSetButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Attribute Relational File Format", "arff");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Save training set");
		File file = fileChooser.showSaveDialog(stage);

		if (file != null){
			saveFile(file);
		}
	}

	
	/**
	 * Changes the scene to the window where you Train an algorithm than decide if a PDF is scientific or not
	 */
	@FXML
	public void changeScene() {
		if(isDataSetLoaded == true) {
			Stage stage = (Stage) saveTrainingSetButton.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../gui/ScientificArticleApp.fxml"));
			AnchorPane myApp;

			try {
				myApp = (AnchorPane) loader.load();
				Scene scene = new Scene(myApp);
				stage.setScene(scene);
				scene.getStylesheets().add(getClass().getResource("../gui/styles.css").toExternalForm());
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error dialog");
			alert.setHeaderText("You have not set the training set!");
			alert.setContentText("You can build up your training set from file or"
					+ " from database.");
			alert.showAndWait();
		}
	}

	
	/**
	 * Load PDF to decide if is scientific or not
	 */
	@FXML
	public void loadPdf() {
		Stage stage = (Stage) loadPdfToDbButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			Scientific sc;
			if(scientificCheck.isSelected()){
				sc = Scientific.SCIENTIFIC;
			}else{
				sc = Scientific.NONSCIENTIFIC;
			}

			TextProcessor tp = new TextProcessor(selectedFile, sc);
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
	 * Load a directory of PDF's
	 */
	@FXML
	public void loadDirectoryOfPDFs() {
		Stage stage = (Stage) loadDirOfPdfButton.getScene().getWindow();
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Load Directory of PDF's");
		File selectedDirectory = chooser.showDialog(stage);
		if(selectedDirectory!=null){
			Scientific sc;
			if(scientificCheck.isSelected()){
				sc = Scientific.SCIENTIFIC;
			}else{
				sc = Scientific.NONSCIENTIFIC;
			}

			File [] pdfs = pdfFinder(selectedDirectory);

			for(int i=0;i<pdfs.length;i++){
				System.out.println("Processed PDF:"+pdfs[i]);
				new TextProcessor(pdfs[i],sc);
			}

		}
	}

}

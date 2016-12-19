package frontend.gui;

import java.io.File;
import java.io.FileWriter;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MyFXController {
	
	private TextProcessor tp;
	
	@FXML
	private Text result;
	
	@FXML
	private Button loadPdfButton;
	
	@FXML
	private ComboBox<String> dataStructCombo;
	
	@FXML
	private ComboBox<String> algorithmCombo;
	
	@FXML
	private Button tempTestButton;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button saveStat;
	
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
	public void initialize(){
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Decision tree",
			        "Other"
			    );
		
		dataStructCombo.setItems(options);
	}
	
	@FXML
	public void backToTraining(){
		 Stage stage= (Stage) backButton.getScene().getWindow();
		 FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(Main.class.getResource("../gui/ScientificArticleApp2.fxml"));
		 AnchorPane myApp;
		try {
			 myApp = (AnchorPane) loader.load();
			 Scene scene = new Scene(myApp);
			 stage.setScene(scene);
			 stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String buildStatisticsString(){
		PDF myPDF=tp.getPDF();
		String statistics="PDF name:"+myPDF.getPath().split("\\\\")[5]+System.getProperty("line.separator")
						+"Is scientific:"+System.getProperty("line.separator")
						+"Page number:"+myPDF.getPagesNr()+System.getProperty("line.separator")
						+"Average words in a row:"+myPDF.getWordsRow()+System.getProperty("line.separator")
						+"Average row/paragraph:"+myPDF.getAvgRowInParagraph()+System.getProperty("line.separator")
						+"Most used font size:"+myPDF.getFontSize()+System.getProperty("line.separator")
						+"Number of images:"+myPDF.getImgNum()+System.getProperty("line.separator")
						+"Bibliography available:"+myPDF.getBibliography();
		
		return statistics;
	}
	
	@FXML
	public void saveStatistics(){
		 Stage stage= (Stage) backButton.getScene().getWindow();
		 FileChooser fileChooser = new FileChooser();
		 
		 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		 fileChooser.getExtensionFilters().add(extFilter);
		 File file = fileChooser.showSaveDialog(stage);
		 
		 if(file != null){
			 try {
				String data=buildStatisticsString();
	            FileWriter fileWriter = null;
	            fileWriter = new FileWriter(file);
	            fileWriter.write(data);
	            fileWriter.close();
	        } catch (IOException ex) {
	        	//logger here later
	      }
		}
	}
	
	private void setLabels(){
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
		mostUsedFontValue.setText(Float.toString(myPDF.getFontSize()));
		mostUsedFontValue.setVisible(true);
		
		numOfImg.setVisible(true);
		numOfImgValue.setText(Integer.toString(myPDF.getImgNum()));
		numOfImgValue.setVisible(true);
		
		bibliography.setVisible(true);
		bibliographyValue.setText(Boolean.toString(myPDF.getBibliography()));
		bibliographyValue.setVisible(true);
	}
		
	@FXML
	public void dataStructSelected(){
		int ind = dataStructCombo.getSelectionModel().getSelectedIndex();
		ObservableList<String> options = null;
		
		switch (ind) {
		case 0:
				options = FXCollections.observableArrayList(
			        "J48"
			    );
			break;
		case 1:
			options = FXCollections.observableArrayList(
		        "Nothing yet"
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
	
	@FXML
	public void trainPushed(){
		String alg = algorithmCombo.getValue();

		if(alg == null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning dialog");
			alert.setHeaderText("You have not choosen an algorithm.");
			alert.setContentText("Please choose an existing algorithm from the combobox.");
			alert.showAndWait();
		}else if(alg.equals("J48")){
			
			PDFContainer.dlp = new DataLearnerPredictor(PDFContainer.lds);
			PDFContainer.dlp.setAlgorithm(LearningAlgorithm.DecisionTree_J48);
			PDFContainer.dlp.train();
		}
	}
	
	@FXML
	public void crossValidation(){
		if(PDFContainer.dlp != null){
			PDFContainer.dlp.crossValidation();
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning dialog");
			alert.setHeaderText("You have not train your dataset.");
			alert.setContentText("Please choose an existing algorithm from the combobox "
					+ "and train your existing dataset.");
			alert.showAndWait();
		}
		
	}
	
	@FXML
	public void loadPdf() {
		if(PDFContainer.dlp != null){
			Stage stage = (Stage) loadPdfButton.getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open File");
			File selectedFile= fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				tp=new TextProcessor(selectedFile,Scientific.UNKNOWN);
				saveStat.setVisible(true);
				//setLabels(tp);
			}		
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning dialog");
			alert.setHeaderText("You have not train your dataset.");
			alert.setContentText("Please choose an existing algorithm from the combobox "
					+ "and train your existing dataset.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void testWeightes() {
		@SuppressWarnings("unused")
		ArrayList<ArrayList<String>> subTitles = 
				common.PDFContainer.lds.getAllSubtitles();	
		
	}
	
}

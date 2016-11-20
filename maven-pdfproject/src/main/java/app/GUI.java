package app;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import common.ConnectionContainer;
import common.PDFContainer;
import mongodb.DataManager;
import mongodb.PDF;
import weka.LearningDataSet;

public class GUI extends JFrame{

	private JPanel centerPanel;

	private void openFile(){
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
	        File file = fc.getSelectedFile();
			TextProcessor tp=new TextProcessor(file,this);
		}
	}

	//
	
	public void setGUI(int pageNumber, float avgWordsInRow, boolean bibliography, float mostUsedFontSizeInPDF){
		JPanel statPanel=new JPanel();
		JLabel statistics=new JLabel("Some statistics about the PDF:");
		statistics.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		statPanel.add(statistics);
		statPanel.setLayout(new GridLayout(10,1));
		centerPanel.add(statPanel,BorderLayout.CENTER);
		JLabel pageNum=new JLabel("Page number: "+pageNumber);
		pageNum.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		statPanel.add(pageNum);
		JLabel avgWord=new JLabel("Average words in a row: "+avgWordsInRow);
		avgWord.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		statPanel.add(avgWord);
		JLabel mostUsedFont=new JLabel("Most used font: "+mostUsedFontSizeInPDF);
		mostUsedFont.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		statPanel.add(mostUsedFont);
		JLabel bibliogrphy=new JLabel("Bibliography available: "+bibliography);
		bibliogrphy.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		statPanel.add(bibliogrphy);
		this.revalidate();			
	}
	
	public GUI(){
		JLabel text;
		this.setSize(530,500);

		text=new JLabel("Welcome to the Scientific Article Recognizer");
		text.setFont(text.getFont().deriveFont(20f));
		
		JPanel northPanel;
		northPanel=new JPanel();
		northPanel.add(text);
		
		centerPanel=new JPanel();
		
		JLabel openText;
		openText=new JLabel("Please select a PDF file:");
		openText.setFont(openText.getFont().deriveFont(16f));

		JButton openFile=new JButton("Open File");
		
		openFile.addActionListener(new ActionListener() {
		    
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
			
		}); 
		
		JButton loadData=new JButton("Build training set from DB");
		loadData.addActionListener(new ActionListener() {
		    
			public void actionPerformed(ActionEvent e) {
				PDF dbData[] = common.ConnectionContainer.dm.findAll(); 
				PDFContainer.lds = new LearningDataSet();
				PDFContainer.lds.addAllPDF(dbData, true);
				PDFContainer.lds.write();
			}
		}); 
		
		
		JButton loadDataFromFile=new JButton("Build training set from file");
		loadDataFromFile.addActionListener(new ActionListener() {
		    
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Attribute Relational File Format", "arff", "text");
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(GUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
			        File file = fc.getSelectedFile();
					PDFContainer.lds = new LearningDataSet(file.getAbsolutePath());
					PDFContainer.lds.write();
				}
			}
		}); 
		
		JButton saveTrainingSet=new JButton("Save training set");
		saveTrainingSet.addActionListener(new ActionListener() {
		    
			public void actionPerformed(ActionEvent e) {
				 
				JFileChooser c = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Attribute Relational File Format", "arff", "text");
				c.setFileFilter(filter);
			      int rVal = c.showSaveDialog(GUI.this);
			      if (rVal == JFileChooser.APPROVE_OPTION) {
			    	  System.out.println(c.getSelectedFile().getName());
			        System.out.println(c.getCurrentDirectory().toString());
			        
			        try{
			            PrintWriter writer = new PrintWriter(c.getCurrentDirectory().toString() + 
			            		"\\" + c.getSelectedFile().getName(), "UTF-8");
			            writer.println(PDFContainer.lds.toString());
			            writer.close();
			        } catch (Exception e2) {
			           e2.printStackTrace();
			        }
			      }
			      if (rVal == JFileChooser.CANCEL_OPTION) {
			        System.out.println("You pressed cancel");
			      }
			}
		});
		
		JPanel fileCPanel=new JPanel();
		fileCPanel.add(openText);
		fileCPanel.add(openFile);
		
		JPanel trainSetCPanel=new JPanel();
		trainSetCPanel.add(loadData);
		trainSetCPanel.add(loadDataFromFile);
		trainSetCPanel.add(saveTrainingSet);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(fileCPanel, BorderLayout.NORTH);
		centerPanel.add(trainSetCPanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(northPanel,BorderLayout.NORTH);
		this.add(centerPanel,BorderLayout.CENTER);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

package app;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mongodb.DataManager;

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
		this.setSize(500,500);

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
		
		JPanel fileCPanel=new JPanel();
		fileCPanel.add(openText);
		fileCPanel.add(openFile);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(fileCPanel, BorderLayout.NORTH);
		
		this.setLayout(new BorderLayout());
		this.add(northPanel,BorderLayout.NORTH);
		this.add(centerPanel,BorderLayout.CENTER);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

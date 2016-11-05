import java.io.BufferedWriter;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Main {

	private static int pageNumber;
	private static float avgWordsInRow;

	private static PDDocument pd = null;
	private static BufferedWriter wr;
	private static StringBuilder sb = null;
	private static String text;
	private static boolean bibliography;
	
	public static void printStatistics(){
		System.out.println("Page number:" + pageNumber);
		System.out.println("Average words/row:" + avgWordsInRow);
		System.out.println("Bibliography available:" + bibliography);
	}
	
	public static boolean bibliographyExistence(String [] rows){

		for (int i = 0; i< rows.length; i++) {
			if(rows[i].contains("References")){
				if(rows[i].length()==11 || rows[i].length()==10){ 
					return true;
				}
			}
		}
		return false;
	}
	
	public static float numberOfWords(String[] rows){
		char c;
		int count=1;
		float sum=(float) 0.0;

		for (int i = 0; i< rows.length; i++) {
			//number of words=number of spaces+1
			count=1;
			for(int j=0;j<rows[i].length();j++){
				c = rows[i].charAt(j);
				if(c ==' '){
					count++;
				}
			}
			
			sum+=count;
		}
		//Every page has a number at the end, that don't counts as a row
		sum=sum - pageNumber;
		int rowNumber=rows.length - pageNumber;
		
		return sum / rowNumber;
	}
	
	public static void processText(){
	
		try {
				File inputFile = new File("E:/BBTE/Csoportos Projekt/PDFs/potra-wright.pdf");
				pd = PDDocument.load(inputFile);
				pageNumber=pd.getNumberOfPages();
				PDFTextStripper stripper = new PDFTextStripper();
				sb = new StringBuilder();
				// Add text to the StringBuilder from the PDF
				stripper.setStartPage(1); // Start extracting from page 3
				stripper.setEndPage(pageNumber); // Extract till page 4
				sb.append(stripper.getText(pd));
				text=sb.toString();

				if (pd != null) {
					pd.close();
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		processText();
		int line=0;
		int wordInLine;

		String[] rows = text.split("\n");
		avgWordsInRow=numberOfWords(rows);
		bibliography=bibliographyExistence(rows);
		printStatistics();
	}

}

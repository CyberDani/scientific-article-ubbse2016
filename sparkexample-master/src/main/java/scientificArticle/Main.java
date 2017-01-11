package scientificArticle;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		String fileName = new String("ubb.pdf");
		File file = new File(fileName);
		TextProcessor tp = new TextProcessor(file, Scientific.SCIENTIFIC);
		PDF pdf = tp.getPDF();
		System.out.println( pdf.getPath() +  " getPath | "  +
		pdf.getPagesNr() +  " getPagesNr | " +
		pdf.getWordsRow() +  " getWordsRow | " +
		pdf.getFontSize() +  " getFontSize | " +
		pdf.getBibliography() +  " getBibliography | " +
		pdf.getAvgRowInParagraph() +  " getAvgRowInParagraph | " +
		pdf.getImgNum() +  " getImgNum | " +
		pdf.getFileSize() +  " getFileSize | " +
		pdf.isScientific() +  " getIsScientific " );
		System.out.println("Program vege.");
	}
}
package backend.weka;

import java.util.HashMap;
import java.util.Map.Entry;

import common.PDFContainer;
import backend.model.PDF;

public class TFIDFforSelectedWords {

	public static double TFIDFCalculation(PDF actualPDF, String specificWord) {
		
		HashMap<String, Integer> wordsFromOnePDF = actualPDF.getWords();
		
		String key;
		int wordOccurrenceInPDFs = 0;
		double nrOfWordOccurrence = 0;
		
		// Counting TF for specificWord.
		for(Entry<String, Integer> entry : wordsFromOnePDF.entrySet()) {
			key = entry.getKey();
			
			if(key.equals(specificWord)) {
				nrOfWordOccurrence = entry.getValue();
				break;
			}
		}
		// If the analysed word is not present in the current pdf, then it's TFIDF is 0.
		if(nrOfWordOccurrence == 0) {
			return 0;
		}
		
		for(int i=0; i<PDFContainer.dbData.size(); i++) {
			
			// Preventing the different type (scientific, non scientific) pdf analysation.
			// We only need the same contra same pdf type.
			if(actualPDF.isScientific() != PDFContainer.dbData.get(i).isScientific()){
				continue;
			}
			
			// Excluding from IDF counting the actual pdf from all pdf if they are the same.
			if(!actualPDF.getPath().equals(PDFContainer.dbData.get(i).getPath())){
				
				// Getting words from 1 PDF.
				HashMap<String, Integer> words = PDFContainer.dbData.get(i).getWords();
				
				for(Entry<String, Integer> entry : words.entrySet()) {
					key = entry.getKey();
					
					if(key.equals(specificWord)) {
						wordOccurrenceInPDFs++;
						break;
					}
				}
			}
		}
		
		// Addig 1 to the divisor will prevent division by zero.
		return (nrOfWordOccurrence * Math.log(PDFContainer.dbData.size()  /  (1+wordOccurrenceInPDFs)));
	}
}

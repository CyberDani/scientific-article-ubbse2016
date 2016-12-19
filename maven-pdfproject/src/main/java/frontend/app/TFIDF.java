package frontend.app;

import java.util.ArrayList;

public class TFIDF {

	private static double wordOccurrence;
	
	public static double tf(String word, String[] allWords, String[] aggregatedWords){
		
		wordOccurrence = 0;
		
		for(String words: allWords){
			if(word.equals(words)){
				wordOccurrence++;
			}
		}

		return wordOccurrence / (double) aggregatedWords.length;
	}
	
	public static double idf(String word, ArrayList<ArrayList<String>> allNumberOfPDFs){
		
		String[] subtitleWordsFromPDF;
		String[] words = {};
		String[] subtitle;
		int size = 0, index = 0;
		float specificPDFs = 0;
		
		for(ArrayList<String> subT : allNumberOfPDFs){
			subtitleWordsFromPDF = subT.toString().split(", ");		// subtitles from a pdf
			for(String sT : subtitleWordsFromPDF){
				size += sT.split(" ").length;
			}
			words = new String[size];
			
			for(int i=0; i<subtitleWordsFromPDF.length; i++){
				subtitle = subtitleWordsFromPDF[i].replaceAll("([\\[:\\?\\\\+\\-\\]\\*\\!\\^\\%\\(\\)\\'\\@\\;\\#\\~\\\"\\/\\<\\>\\.\\&\\|¬\\`\\\\])", "").split(" ");		// 1 subtitle from pdf which is cleared from unwanted characters
				for(String wd : subtitle){
					words[index] = wd;
					index++;
				}
			}
			index = 0;
			
			for(int j=0; j<words.length; j++){
				if(words[j].equals(word)){			// checking if the word is present in the current pdf.
					specificPDFs++;
					break;
				}
			}
		}
		
		return Math.log((float) allNumberOfPDFs.size()  /  (1+specificPDFs));	// addig 1 to the divisor will prevent division by zero
	}
}

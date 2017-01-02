package backend.model;

import java.util.HashMap;

import common.Scientific;

public class PDF {
	private Object _id;
	private String path;
	private HashMap<String, Integer> words;
	private int pagesNr;
	private double wordsRow;
	private double fontSize;
	private boolean bibliography;
	private int avgRowInParagraph;
	private int imgNum;
	private long fileSize;
	private Boolean scientific;

	public PDF(String path, HashMap<String, Integer> words, int pagesNr, double wordsRow, 
			float fontSize,  int imgNum, int averageRowInParagraph, 
			Boolean bibliography, long fileSize , Scientific sc) {
		setPath(path);
		setWords(words);
		setPagesNr(pagesNr);
		setWordsRow(wordsRow);
		setFontSize(fontSize);
		setBibliography(bibliography);
		setImgNum(imgNum);
		setAvgRowInParagraph(averageRowInParagraph);
		setFileSize(fileSize);
		
		scientific = null;
		
		if(sc == Scientific.SCIENTIFIC){
			setScientific(true);
		}else if(sc == Scientific.NONSCIENTIFIC){
			setScientific(false);
		}
		
		
	}

	public PDF()
	{
		setPath("");
		setWords(null);
		setPagesNr(0);
		setWordsRow(0);
		setFontSize(0);
		setBibliography(false);
	}
	
	public HashMap<String, Integer> getWords() {
		return words;
	}

	public void setWords(HashMap<String, Integer> words) {
		this.words = words;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public boolean isScientific() {
		return scientific;
	}

	public void setScientific(boolean scientific) {
		this.scientific = scientific;
	}

	public int getImgNum() {
		return imgNum;
	}

	public void setImgNum(int imgNum) {
		this.imgNum = imgNum;
	}

	public int getAvgRowInParagraph() {
		return avgRowInParagraph;
	}

	public void setAvgRowInParagraph(int avgRowInParagraph) {
		this.avgRowInParagraph = avgRowInParagraph;
	}
	
	public void setBibliography(boolean bibliography) {
		this.bibliography = bibliography;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPagesNr() {
		return pagesNr;
	}

	public void setPagesNr(int pagesNr) {
		this.pagesNr = pagesNr;
	}

	public double getWordsRow() {
		return wordsRow;
	}

	public void setWordsRow(double wordsRow) {
		this.wordsRow = wordsRow;
	}

	public double getFontSize() {
		return fontSize;
	}

	public void setFontSize(double fontSize) {
		this.fontSize = fontSize;
	}

	public Boolean getBibliography() {
		return bibliography;
	}

	public void setBibliography(Boolean bibliography) {
		this.bibliography = bibliography;
	}
	
	public Object getoid() {
		return _id;
	}

	public void set_id(Object _id) {
		this._id = _id;
	}


}

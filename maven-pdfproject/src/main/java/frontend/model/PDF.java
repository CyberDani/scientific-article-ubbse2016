package frontend.model;

import common.Scientific;

public class PDF {
	private Object _id;
	private String path;
	private String[] subtitles;
	private int pagesNr;
	private double wordsRow;
	private float fontSize;
	private boolean bibliography;
	private int avgRowInParagraph;
	private int imgNum;
	private Boolean scientific;

	public PDF(String path, String[] subtitles, int pagesNr, double wordsRow, 
			float fontSize,  int imgNum, int averageRowInParagraph, 
			Boolean bibliography, Scientific sc) {
		setPath(path);
		setSubtitles(subtitles);
		setPagesNr(pagesNr);
		setWordsRow(wordsRow);
		setFontSize(fontSize);
		setBibliography(bibliography);
		setImgNum(imgNum);
		setAvgRowInParagraph(averageRowInParagraph);
		
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
		setSubtitles(null);
		setPagesNr(0);
		setWordsRow(0);
		setFontSize(0);
		setBibliography(false);
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

	public String[] getSubtitles() {
		return subtitles;
	}

	public void setSubtitles(String[] subtitles) {
		this.subtitles = subtitles;
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

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
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

package mongodb;

public class PDF {
	private Object _id;
	private String path;
	private String[] subtitles;
	private int pagesNr;
	private double wordsRow;
	private String fontSize;
	private boolean bibliography;
	private int avgRowInParagraph;
	private int imgNum;

	public PDF(String path, String[] subtitles, int pagesNr, double wordsRow, String fontSize,  int imgNum, int averageRowInParagraph, Boolean bibliography) {
		setPath(path);
		setSubtitles(subtitles);
		setPagesNr(pagesNr);
		setWordsRow(wordsRow);
		setFontSize(fontSize);
		setBibliography(bibliography);
		setImgNum(imgNum);
		setAvgRowInParagraph(averageRowInParagraph);
	}

	public PDF()
	{
		setPath("");
		setSubtitles(null);
		setPagesNr(0);
		setWordsRow(0);
		setFontSize("0.0");
		setBibliography(false);
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

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
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

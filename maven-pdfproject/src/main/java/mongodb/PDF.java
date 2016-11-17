package mongodb;

public class PDF {
	private String path;
	private String[] subtitles;
	private int pagesNr;
	private double wordsRow;
	private String fontSize;
	private Boolean bibliography;

	public PDF(String path, String[] subtitles, int pagesNr, double wordsRow, String fontSize, Boolean bibliography) {
		setPath(path);
		setSubtitles(subtitles);
		setPagesNr(pagesNr);
		setWordsRow(wordsRow);
		setFontSize(fontSize);
		setBibliography(bibliography);
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

}

package mongodb;

public class Main {

	public static void main(String[] args) {
		MongoDBJDBC db = new MongoDBJDBC("PDFdata", "localhost", 27017);
		try {
			db.createConnection();
			DataManager dm = new DataManager(db.getDatabase());
			//dm.createCollection("LearningData");
			
			String[] subtitles = {"aa1", "aa2"};
			dm.insertDocument("LearningData", "a1", subtitles, "a3", "a4", "a5", true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			db.closeConnection();
		}
	}
}

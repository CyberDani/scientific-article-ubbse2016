package mongodb;

public class Main {

	public static void main(String[] args) {
		MongoDBJDBC db = new MongoDBJDBC("PDFdata", "localhost", 27017);
		try {
			db.createConnection();
			DataManager dm = new DataManager(db.getDatabase());
			//dm.createCollection("LearningData");
			
			String[] subtitles = {"Introdution", "Abtsract"};
			PDF pdf = new PDF("home/LiuLiuCong", subtitles, 10, 10.3, "11", true);
			dm.insertDocument("LearningData", pdf);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			db.closeConnection();
		}
	}
}

package mongodb;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DataManager {
	private MongoDatabase db; 
	
	public DataManager(MongoDatabase db) {
		this.db = db;
	}
	
	public void createCollection(String collection) {
		db.createCollection(collection);				
	}
	
	public JSONObject convertDataToJSON(String path, String[] subtitles, String pagesNr, String wordsRow, String fontSize, Boolean bibliography) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();

		for (String i : subtitles) {
			array.add(i);
		}

		result.put("path", path);
		result.put("subtitles", array);
		result.put("pagesNr", path);
		result.put("wordsRow", wordsRow);
		result.put("fontSize", fontSize);
		result.put("bibligraphy", bibliography.toString());

		System.out.println(result.toString());
		return result;
	}

	public void insertDocument(String collection, String path, String[] subtitles, String pagesNr, String wordsRow, String fontSize, Boolean bibliography) {
		JSONObject dataJSON = convertDataToJSON(path, subtitles, pagesNr, wordsRow, fontSize, bibliography);
		
		MongoCollection<Document> coll = db.getCollection(collection);
		Document doc = Document.parse(dataJSON.toString());
		coll.insertOne(doc);
	}
	
}

package mongodb;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	public void insertDocument(String collection, PDF pdf) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(pdf);
		System.out.println(json);

		MongoCollection<Document> coll = db.getCollection(collection);
		Document doc = Document.parse(json);
		coll.insertOne(doc);
	}

}

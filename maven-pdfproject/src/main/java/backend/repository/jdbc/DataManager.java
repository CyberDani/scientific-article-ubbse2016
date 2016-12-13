package backend.repository.jdbc;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import backend.model.PDF;

public class DataManager {
	private ConnectionManager cm;
	private MongoDatabase db; 

	public DataManager() {
		ConnectionManager.getInstance();
		this.db = cm.getDatabase();
	}

	public void createCollection(String collection) {
		db.createCollection(collection);				
	}

}

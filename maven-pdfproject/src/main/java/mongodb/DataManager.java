package mongodb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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

	public void findAll() {

		MongoCollection<Document> coll = db.getCollection("LearningData"); 
		System.out.println("Collection mycol selected successfully"); 

		MongoCursor<Document> cursor = coll.find().iterator();
		Set<Entry<String, 
		Object>> a;

		ObjectMapper mapper = new ObjectMapper();

		while (cursor.hasNext()) { 

			String PDFJson = cursor.next().toJson();
			Map<String,Object> myMap = new HashMap<String, Object>();
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				myMap = objectMapper.readValue(PDFJson, HashMap.class);
				
				for (Entry<String, Object> e : myMap.entrySet()) {
				    String key = e.getKey();
				    Object value  = e.getValue();
				    if (value != null)
				    {
				    	System.out.println("Key: " + key + " value: " + value.toString());
				    }
				    
				}
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Map is: "+myMap);
			//eturn cursor;
		}

	}
	
}

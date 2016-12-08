package mongodb;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.CommonDataSource;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.Converter;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import common.PDFContainer;
import common.Tools;

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

	public PDF[] findAll() {

		// collection lekerese
		MongoCollection<Document> coll = db.getCollection("LearningData"); 

		MongoCursor<Document> cursor = coll.find().iterator();
		//ObjectMapper mapper = new ObjectMapper();

		
		// adat hosszusaganak meghatarozasa
		int length = 0;
		
		while (cursor.hasNext()) {
			cursor.next();
			++length;
		}
		PDF answer[] = new PDF[length];
		
		
		// Iterator ujradefinialasa
		cursor = coll.find().iterator();
		
		
		// adatok kinyerese es PDF osztalyba valo konvertalasa
		Field declaredField =  null;
		int nr = 0;
		while (cursor.hasNext()) { 

			PDF temp = new PDF();
			
			String PDFJson = cursor.next().toJson();
			Map<String,Object> myMap = new HashMap<String, Object>();
			ObjectMapper objectMapper = new ObjectMapper();
			
			try {
				
				
				myMap = objectMapper.readValue(PDFJson, HashMap.class);
				
				for(int i = 1;i<PDFContainer.PDFAttrNames.length;++i){
					try {
						declaredField = 
								PDF.class.getDeclaredField(PDFContainer.PDFAttrNames[i]);
					} catch (NoSuchFieldException e2) {
						e2.printStackTrace();
					} catch (SecurityException e2) {
						e2.printStackTrace();
					}
					
					declaredField.setAccessible(true);
					
					try {
						// Convert ArrayList to Array(String, Integer, Double, Float)
						if(declaredField.getType() == String[].class){
							@SuppressWarnings("unchecked")
							ArrayList<String> data = (ArrayList<String>)myMap.get(PDFContainer.PDFAttrNames[i]);
							if(data == null)
							{
								data = new ArrayList<String>();
								data.add(common.PDFContainer.nullString);
							}
							String[] newData = new String[data.size()];
							newData = data.toArray(newData);
							declaredField.set(temp, newData);
						}else if(declaredField.getType() == int[].class){
							ArrayList<Integer> data = (ArrayList<Integer>)myMap.get(PDFContainer.PDFAttrNames[i]);
							int newData[] = Tools.intArrListToArray(data);
							declaredField.set(temp, newData);
						}else if(declaredField.getType() == double[].class){
							ArrayList<Double> data = (ArrayList<Double>)myMap.get(PDFContainer.PDFAttrNames[i]);
							double newData[] = Tools.doubleArrListToArray(data);
							declaredField.set(temp, newData);
						}else if(declaredField.getType() == float[].class){
							ArrayList<Float> data = (ArrayList<Float>)myMap.get(PDFContainer.PDFAttrNames[i]);
							float newData[] = Tools.floatArrListToArray(data);
							declaredField.set(temp, newData);
						}else{
							declaredField.set(temp, myMap.get(PDFContainer.PDFAttrNames[i]));
						}
						
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}	
				}
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//System.out.println("");
			@SuppressWarnings("rawtypes")
			LinkedHashMap id = (LinkedHashMap) myMap.get("_id");
			Object key = id.keySet().iterator().next();

			Object value = id.get(key);
			temp.set_id(value);
			answer[nr++] = temp;
		}

		return answer;
	}

	private Class<?> Class(Class<String[]> class1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

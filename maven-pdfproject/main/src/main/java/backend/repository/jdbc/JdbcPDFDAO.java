package backend.repository.jdbc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import backend.repository.PDFDAO;
import common.PDFContainer;
import common.Tools;
import backend.model.PDF;

public class JdbcPDFDAO implements PDFDAO {

	private final ConnectionManager cm;

	/**
	 *  Create an instance from ConnectionManager => create connection with MongoDB
	 */
	public JdbcPDFDAO() {
		cm = ConnectionManager.getInstance();
	}

	/**
	 * Return list of all PDF from database, from LearningData collection
	 */
	@SuppressWarnings("unchecked")
	public List<PDF> getAllPDFs() {
		/**
		 *  Get a collection from database
		 */
		MongoCollection<Document> coll = cm.getDatabase().getCollection("LearningData"); 

		MongoCursor<Document> cursor = coll.find().iterator();

		List<PDF> answer = new ArrayList<PDF>();


		/**
		 * Redefine the iterator
		 */
		cursor = coll.find().iterator();

		/**
		 * Get data from database and convert to PDF
		 */
		Field declaredField =  null;
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
						/**
						 *  Convert ArrayList to Array(String, Integer, Double, Float)
						 */
						if(declaredField.getType() == String[].class){
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
						}else if(declaredField.getType() == HashMap.class){
							HashMap<String,Integer> data = (HashMap<String,Integer>)myMap.get(PDFContainer.PDFAttrNames[i]);
							declaredField.set(temp, data);
						} else if(declaredField.getType() == String.class) {
							declaredField.set(temp,(myMap.get(PDFContainer.PDFAttrNames[i])).toString());

						} else if(declaredField.getType() == Double.class) {
							declaredField.set(temp,Double.parseDouble((myMap.get(PDFContainer.PDFAttrNames[i])).toString()));

						} else {
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

			@SuppressWarnings("rawtypes")
			LinkedHashMap id = (LinkedHashMap) myMap.get("_id");
			Object key = id.keySet().iterator().next();

			Object value = id.get(key);
			temp.set_id(value);
			answer.add(temp);
		}

		return answer;
	}
	
	/**
	 * 
	 * Insert a PDF into database in a collection 
	 * 
	 */
	public void insertPDF(String collection, PDF pdf) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(pdf);
		//System.out.println(json);

		MongoCollection<Document> coll = cm.getDatabase().getCollection(collection);
		Document doc = Document.parse(json);
		coll.insertOne(doc);
	}

}

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

	public JdbcPDFDAO() {
		cm = ConnectionManager.getInstance();
	}

	public List<PDF> getAllPDFs() {
		// collection lekerese
		MongoCollection<Document> coll = cm.getDatabase().getCollection("LearningData"); 

		MongoCursor<Document> cursor = coll.find().iterator();
		//ObjectMapper mapper = new ObjectMapper();


		// adat hosszusaganak meghatarozasa
		int length = 0;

		while (cursor.hasNext()) {
			cursor.next();
			++length;
		}
		List<PDF> answer = new ArrayList<PDF>();


		// Iterator ujradefinialasa
		cursor = coll.find().iterator();

		// adatok kinyerese es PDF osztalyba valo konvertalasa
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
			answer.add(temp);
		}

		return answer;
	}

}

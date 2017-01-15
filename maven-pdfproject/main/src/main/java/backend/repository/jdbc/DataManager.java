package backend.repository.jdbc;

import com.mongodb.client.MongoDatabase;
/**
 * 
 * Class for data(from database) managing 
 *
 */
public class DataManager {
	private ConnectionManager cm;
	private MongoDatabase db; 

	/**
	 *  
	 *  Initialize a connection with MongoDB database
	 * 
	 */
	public DataManager() {
		ConnectionManager.getInstance();
		this.db = cm.getDatabase();
	}

	/**
	 * 
	 * Create a collection in MongoDB database with given name
	 * 
	 * @param collection - name of the new collection
	 */
	public void createCollection(String collection) {
		db.createCollection(collection);				
	}

}

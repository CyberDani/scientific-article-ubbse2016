package backend.repository.jdbc;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
/**
 * 
 * Class for database connection managing
 *
 */
public class ConnectionManager {

	private static Object lock = new Object();
	private static ConnectionManager INSTANCE;
	private MongoDatabase db; 
	private MongoClient mongoClient;

	/**
	 *  The constructor call the createConnection() public void
	 */
	public ConnectionManager() {
		createConnection();
	}

	/**
	 * 
	 * Return an instance of class
	 * 
	 * @return If the ConnectionManager class has an instance, return this, else null
	 */
	public static ConnectionManager getInstance() {
		if (INSTANCE == null) {
			synchronized (lock) {
				if (INSTANCE == null) {
					INSTANCE = new ConnectionManager();
				}
			}	
		}
		return INSTANCE;
	}

	/**
	 * 
	 * Create connection with local MongoDB database at 27017 port
	 * 
	 */
	public void createConnection() {
		mongoClient = null;
		mongoClient = new MongoClient("localhost", 27017);
		System.out.println(mongoClient);
		db = mongoClient.getDatabase("PDFdata");
		
		if (db != null) {
			System.out.println("Connect to database successfully");
		} else {
			System.out.println("Connect creating failed.");
			
		}
	}
	
	/**
	 * 
	 * Close the connection with local MongoDB database.
	 * 
	 */
	public void closeConnection() {
		if (mongoClient != null) {
			mongoClient.close();
		}
		INSTANCE = null;
		//mongoClient.
		//System.out.println(mongoClient);
		System.out.println("Connection closed!");
	}
	/**
	 * 
	 * Return the database
	 * 
	 * @return the MongoDB database
	 */
	public MongoDatabase getDatabase() {
		return db;
	}
	
	public MongoClient getMongoClient() {
		return this.mongoClient;
	}
	/*
	public void createUser(String user, String password, String roles){
		Map<String, Object> commandArguments = new BasicDBObject();
		commandArguments.put("createUser", user);
		commandArguments.put("pwd", password);
		commandArguments.put("roles", roles);
		BasicDBObject command = new BasicDBObject(commandArguments);
		db.runCommand(command);
	}
	 */
}

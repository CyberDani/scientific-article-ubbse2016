package backend.repository.jdbc;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectionManager {

	private static Object lock = new Object();
	private static ConnectionManager INSTANCE;
	private MongoDatabase db; 
	private MongoClient mongoClient;

	public ConnectionManager() {
		createConnection();
	}

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

	public void createConnection() {
		mongoClient = null;
		mongoClient = new MongoClient("localhost", 27017);
		// New way to get database
		db = mongoClient.getDatabase("PDFdata");
		System.out.println("Connect to database successfully");
	}
	
	public void closeConnection() {
		if (mongoClient != null) {
			mongoClient.close();
		}
		System.out.println("Connection closed!");
	}
	
	public MongoDatabase getDatabase() {
		return db;
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

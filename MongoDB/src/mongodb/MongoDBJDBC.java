package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBJDBC {
	private String database;
	private String URI;
	private int port;
	private MongoDatabase db; 
	private MongoClient mongoClient;

	public MongoDBJDBC(String database, String URI, int port ) {
		this.database = database;
		this.URI = URI;
		this.port = port;
	}

	public void createConnection() {
		mongoClient = null;
		mongoClient = new MongoClient(URI, port);
		// New way to get database
		db = mongoClient.getDatabase(database);
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
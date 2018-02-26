package br.com.testezup.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConnectionFactory {

	public Connection getConnection() throws SQLException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zupdb", "root", "root");	
			return con;
		} catch (ClassNotFoundException ex) {
			throw new SQLException(ex);
		}
		
	}
	
	public MongoDatabase getMongoDataBase() throws Exception {
		try{
			MongoClientURI clientURI = new MongoClientURI("mongodb://localhost:27017/zupdb");
			MongoClient mongoClient = new MongoClient(clientURI);			
			MongoDatabase db = mongoClient.getDatabase("zupdb");						
			return db;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

}


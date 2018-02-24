package br.com.testezup.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mysql.jdbc.*;

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
	
	public MongoClient getClient() {
		try{
			Class.forName("");
			MongoClientURI clientURI = new MongoClientURI("");
			MongoClient mongoClient = new MongoClient(clientURI);
			
			return mongoClient;
		} catch (Exception ex) {
			
		}
	}

}


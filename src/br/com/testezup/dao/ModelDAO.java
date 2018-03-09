package br.com.testezup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ValidationOptions;

import br.com.testezup.models.Model;
import br.com.testezup.sql.Statements;

public class ModelDAO {

	private Connection con;
	private MongoDatabase db;
	Savepoint save;
	
	public ModelDAO(){
		try{
			this.con = new ConnectionFactory().getConnection();
			this.db = new ConnectionFactory().getMongoDataBase();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public ModelDAO(Connection con){
		this.con = con;		
	}
	
	public ModelDAO(MongoDatabase db){
		this.db = db;		
	}	
	
	public MongoDatabase getDb() {
		return db;
	}

	public void setDb(MongoDatabase db) {
		this.db = db;
	}

	//Retorna todos os modelos
	public List<String> getModels(){
		try{
			List<String> models = new ArrayList<String>();
			String query = Statements.getModels();
			PreparedStatement stmt = con.prepareStatement(query);					
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){								
				models.add(rs.getString("name"));
			}
			
			rs.close();
			stmt.close();
			
			return models;
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}	
	
	public List<Document> getModelsMongo() {
		try{
			List<Document> docs = new ArrayList<Document>();
			MongoCollection collection = db.getCollection("models");
			FindIterable<Document> foundDocs = collection.find();
			foundDocs.projection(Projections.excludeId());
			
			for(Document doc : foundDocs){
				docs.add(doc);
			}
			
			return docs;
		} catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	//Retorna um modelo específico
	public Model getModel(String modelName){
		try{
			Model model = new Model();
			String query = Statements.getModel();
			PreparedStatement stmt = con.prepareStatement(query);					
			
			stmt.setString(1, modelName);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){								
				model.setModelName(rs.getString("name"));
				model.setPrimarykey(rs.getString("primarykey"));
			}
			
			rs.close();
			stmt.close();
			
			return model;
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public Document getModelMongo(String modelName) {
		Document doc = new Document();
		MongoCollection collection = db.getCollection("models");
		doc = (Document) collection.find(new Document("name",modelName)).projection(Projections.excludeId()).first();
		
		return doc;
	}
	
	public String getModelIdentifier(String modelName){
		Document doc = new Document();
		MongoCollection collection = db.getCollection("models");
		doc = (Document) collection.find(new Document("name",modelName)).projection(Projections.excludeId()).first();		
		return ((Document) doc.get("rules")).get("identifier").toString();		
	}
	
	//Retorna um mapeamento de quais são os atributos e seu tipo
	public Map<String,String> getAttributes(String modelName){
		try{
			Map<String,String> attributes = new HashMap<String,String>();
			String query = Statements.getAttributes();
			PreparedStatement stmt = con.prepareStatement(query);					
			
			stmt.setString(1, modelName);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){								
				attributes.put(rs.getString("attrname"),rs.getString("attrtype"));
			}
			
			rs.close();
			stmt.close();
			
			return attributes;
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void insertNewModel(Model model){
		try{
			String query = Statements.insertNewModel();
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, model.getModelName().toLowerCase());
			stmt.setString(2, model.getPrimarykey());
			
			stmt.execute();
			stmt.close();
			
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void createNewModelMongo(Document doc, ValidationOptions options, List<String> uniqueFields){
		try{
			MongoCollection collection = db.getCollection("models");			
			collection.insertOne(doc);
			db.createCollection(doc.get("name").toString());
			//createNewMongoCollection(doc,options);
			
			collection = db.getCollection(doc.get("name").toString());
			for(String field : uniqueFields){
				createNewUniqueIndex(collection,field);
			}
		} catch	(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void createNewMongoCollection(Document doc, ValidationOptions options){
		db.createCollection(doc.get("name").toString(), new CreateCollectionOptions().validationOptions(options));
	}
	
	public void createNewUniqueIndex(MongoCollection collection, String field){
		collection.createIndex(new Document(field,1), new IndexOptions().unique(true));
	}

	public void insertNewModelAttributes(String modelName, String attrname, String attrtype) {
		try{
			String query = Statements.insertNewModelAttributes();
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, modelName);
			stmt.setString(2, attrname);
			stmt.setString(3, attrtype);
			
			stmt.execute();
			stmt.close();
						
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}

	public void createNewModel(String modelName, String columns, String pk) {
		try{
			String query = Statements.createModel(modelName,columns,pk);
			PreparedStatement stmt = con.prepareStatement(query);			
			
			stmt.execute();
			stmt.close();
			
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}

	public void deleteModel(String id) {
		try{
			String query = Statements.deleteModel();
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			stmt.execute();
			stmt.close();
			
			query = Statements.dropTable(id);
			stmt = con.prepareStatement(query);
			
			stmt.execute();
			stmt.close();
			
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void deleteModelMongo(String id){
		try{
			MongoCollection collection = db.getCollection(id);
			collection.drop();
			collection = db.getCollection("models");
			collection.findOneAndDelete(new Document("name",id));			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}

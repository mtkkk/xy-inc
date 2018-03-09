package br.com.testezup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import br.com.testezup.models.DynamicModelEntry;
import br.com.testezup.sql.Statements;

public class DynamicModelDAO {

	private Connection con;
	private MongoDatabase db;
	Savepoint save;
	
	public DynamicModelDAO(){
		try{
			this.con = new ConnectionFactory().getConnection();
			this.db = new ConnectionFactory().getMongoDataBase();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public DynamicModelDAO(Connection con){
		this.con = con;
	}
	
	public DynamicModelDAO(MongoDatabase db){
		this.db = db;
	}
	
	public JSONArray getDynamicModels(String modelName){
		try{			
			JSONArray jArray = new JSONArray();
			
			String query = Statements.getDynamicModels(modelName);
			PreparedStatement stmt = con.prepareStatement(query);					
						
			
			ResultSet rs = stmt.executeQuery();						
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//Através do MetaData define os tipos de cada coluna
			List<String> types = new ArrayList<String>();
			for(int i = 1; i <= rsmd.getColumnCount(); i++){
				types.add(rsmd.getColumnTypeName(i));
			}
			
			while(rs.next()){
				JSONObject jObj = new JSONObject();
				for(int i = 0; i < types.size(); i++){
					addToJson(types.get(i),jObj,rsmd,rs,i);
				}
				jArray.put(jObj);
			}
			
			rs.close();
			stmt.close();
			
			return jArray;
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}	
	
	public List<Document> getDynamicModelsMongo(String modelName) {
		try{
			List<Document> docs = new ArrayList<Document>();
			MongoCollection collection = db.getCollection(modelName);
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
	
	public JSONObject getDynamicModel(String modelName, String id) throws Exception{
		try{
			
			JSONObject jObj = new JSONObject();
			
			String key = getPrimaryKey(modelName);
						
			String query = Statements.getDynamicModel(modelName, key);
			PreparedStatement stmt = con.prepareStatement(query);				
			
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//Através do MetaData define os tipos de cada coluna
			List<String> types = new ArrayList<String>();
			for(int i = 1; i <= rsmd.getColumnCount(); i++){
				types.add(rsmd.getColumnTypeName(i));
			}
			
			while(rs.next()){
				for(int i = 0; i < types.size(); i++){
					addToJson(types.get(i),jObj,rsmd,rs,i);
				}
			}
			
			rs.close();
			stmt.close();
			
			return jObj;
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public Document getDynamicModelMongo(String modelName, String id){
		try{
			Document doc = new Document();
			MongoCollection collection = db.getCollection(modelName);
			
			//TODO
			//doc = (Document) collection.find(new Document("name",id)).projection(Projections.excludeId()).first();
			
			return doc;
		} catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void createDynamicModelEntry(String modelName, String columns, String values, Map<String,String> attributes, DynamicModelEntry entry){
		try{			
			String query = Statements.createDynamicModelEntry(modelName,columns,values);
			PreparedStatement stmt = con.prepareStatement(query);			
			
			setVariables(stmt,columns,attributes,entry);
			
			stmt.execute();
			stmt.close();
						
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	public void createDynamicModelEntryMongo(Document doc, String modelName) {
		try{
			MongoCollection collection = db.getCollection(modelName);
			collection.insertOne(doc);			
		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void updateDynamicModelEntry(String modelName, String columns, Map<String, String> attributes,
			DynamicModelEntry dmEntry, String id) throws Exception {
		try{
			String key = getPrimaryKey(modelName);
			String query = Statements.updateDynamicModelEntry(modelName,columns,key,id);
			PreparedStatement stmt = con.prepareStatement(query);			
			
			setVariables(stmt,columns,attributes,dmEntry);
			
			stmt.execute();
			stmt.close();
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void updateDynamicModelEntryMongo(Bson operation, String modelName){
		try{			
			MongoCollection collection = db.getCollection(modelName);
			//TODO - SELECIONAR O DOCUMENTO ANTES
			Document foundDoc = new Document();
			collection.updateOne(foundDoc, operation);
			
		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void deleteDynamicModelEntry(String modelName, String id) throws Exception {
		try{
			String key = getPrimaryKey(modelName);
			String query = Statements.deleteDynamicModelEntry(modelName,id,key);
			PreparedStatement stmt = con.prepareStatement(query);					
			
			stmt.execute();
			stmt.close();
			
			
		}  catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	public String getPrimaryKey(String modelName) throws Exception{
		try{
			//Buscar a chave primária para este modelo
			String key;
			String query = Statements.getPrimaryKey();
			PreparedStatement stmt = con.prepareStatement(query);	
			
			stmt.setString(1, modelName);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				key = rs.getString("primarykey");
			} else {
				throw new Exception("Modelo não possui chave primária");
			}	
			
			stmt.close();
			rs.close();	
			
			return key;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
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
	
	//Adiciona ao json a chave e o valor baseado no tipo da coluna
	private void addToJson(String type, JSONObject jObj, ResultSetMetaData rsmd, ResultSet rs, int i){
		try {
			switch(type.toLowerCase()){
			case "varchar":
				jObj.put(rsmd.getColumnName(i+1), rs.getString(i+1));
				break;
			case "int":
				jObj.put(rsmd.getColumnName(i+1), rs.getInt(i+1));
				break;					
			case "double":
				jObj.put(rsmd.getColumnName(i+1), rs.getDouble(i+1));
				break;
			case "float":
				jObj.put(rsmd.getColumnName(i+1), rs.getFloat(i+1));
				break;
			case "date":
				jObj.put(rsmd.getColumnName(i+1), rs.getDate(i+1));
				break;					
			}
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}
	
	//Setta os valores no PreparedStatement baseado no tipo de cada valor
	private void setVariables(PreparedStatement stmt, String columnsString, Map<String,String> attributes, DynamicModelEntry entry){
		try {
			String[] columns = columnsString.split(",");
			int iterator = 1;
			for(String col : columns){
				col = col.replace("?", "").replace("=", "").trim();
				switch(attributes.get(col).toLowerCase()){
				case "string":
					stmt.setString(iterator, entry.get(col));
					break;
				case "int":
					stmt.setInt(iterator, Integer.parseInt(entry.get(col)));
					break;					
				case "double":
					stmt.setDouble(iterator, Double.parseDouble(entry.get(col)));
					break;
				case "float":
					stmt.setFloat(iterator, Float.parseFloat(entry.get(col)));
					break;
				case "date":
					LocalDate date =  LocalDate.parse(entry.get(col));					  
					stmt.setObject(iterator, date);
					break;					
				}
				iterator++;
			}
			
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}

	
	
}

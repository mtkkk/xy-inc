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

import br.com.testezup.models.Model;
import br.com.testezup.sql.Statements;

public class ModelDAO {

	private Connection con;
	Savepoint save;
	
	public ModelDAO(){
		try{
			this.con = new ConnectionFactory().getConnection();					
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public ModelDAO(Connection con){
		this.con = con;
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
}

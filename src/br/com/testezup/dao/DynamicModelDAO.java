package br.com.testezup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.testezup.models.Model;
import br.com.testezup.sql.Statements;

public class DynamicModelDAO {

	private Connection con;
	Savepoint save;
	
	public DynamicModelDAO(){
		try{
			this.con = new ConnectionFactory().getConnection();					
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public DynamicModelDAO(Connection con){
		this.con = con;
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
				for(int i = 1; i < types.size(); i++){
					//Adiciona ao json a chave e o valor baseado no tipo da coluna
					switch(types.get(i).toLowerCase()){
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
	
	public Model getModel(String modelName){
		try{
			Model model = new Model();
			String query = Statements.getModel();
			PreparedStatement stmt = con.prepareStatement(query);					
			
			stmt.setString(1, modelName);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){								
				model.setModelName(rs.getString("name"));
			}
			
			rs.close();
			stmt.close();
			
			return model;
		} catch (SQLException ex){
			throw new RuntimeException(ex);
		}
	}
}

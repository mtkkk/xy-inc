package br.com.testezup.sql;

public class Statements {

	
	/*
	 * Statements Models
	 */
	public static String insertNewModel(){
		String sql = "insert into models (name,creationdate,primarykey) values (?,sysdate(),?)";
		
		return sql;
	}
	
	public static String insertNewModelAttributes() {
		String sql = "insert into modelattributes (modelname,attrname,attrtype) values(?,?,?)";
		
		return sql;
	}
	
	public static String getModels(){
		String sql = "select name from models where name not in ('adminunittest')";
		
		return sql;
	}
	
	public static String getModel(){
		String sql = "select name, primarykey from models " +
						"where name = ? ";
		
		return sql;
	}
	
	public static String getAttributes(){
		String sql = "select attrname, attrtype from modelattributes where modelname = ? ";
		
		return sql;
	}
	
	public static String createModel(String modelName, String columns, String pk){
		String sql = "create table " + modelName +
						" (" +						
						columns +
						"primary key("+pk+")" +
						")";
		
		return sql;
	}
	
	public static String deleteModel(){
		String sql = "delete from models where name = ?";
		
		return sql;
	}
	
	public static String getPrimaryKey(){
		String sql = "select primarykey from models where name = ? ";
		
		return sql;
	}
	
	public static String dropTable(String modelName){
		String sql = "drop table " + modelName;
		
		return sql;
	}

	/*
	 * Statements DynamicModels
	 */
	public static String getDynamicModels(String modelName){
		String sql = "select * from " + modelName;
		
		return sql;
	}
	
	public static String getDynamicModel(String modelName, String key){
		String sql = "select * from " + modelName + " where " + key + " = ?";
		
		return sql;
	}
		
	public static String createDynamicModelEntry(String modelName, String columns, String values){
		String sql = "insert into " + modelName + "(" + columns + ") values " + values;
		
		return sql;
	}
	
	public static String updateDynamicModelEntry(String modelName, String columns, String key, Object id){
		String idStr;
		if(key instanceof String){
			idStr = "'" + id + "'";
		} else {
			idStr = id.toString();
		}
		
		String sql = "update " + modelName + " set " + columns +						
						" where " + key + " = " + idStr;
		
		return sql;
	}

	public static String deleteDynamicModelEntry(String modelName, Object id, String key) {
		String idStr;
		if(key instanceof String){
			idStr = "'" + id + "'";
		} else {
			idStr = id.toString();
		}
		
		String sql = "delete from " + modelName + " where " + key + " = " + idStr;
		
		return sql;
	}
}

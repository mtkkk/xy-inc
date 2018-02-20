package br.com.testezup.sql;

public class Statements {

	
	/*
	 * Statements MODELS
	 */
	public static String insertNewModel(){
		String sql = "insert into models (name,creationdate) values (?,sysdate())";
		
		return sql;
	}
	
	public static String insertNewModelAttributes() {
		String sql = "insert into modelattributes (modelname,attrname,attrtype) values(?,?,?)";
		
		return sql;
	}	
	public static String getModels(){
		String sql = "select name from models";
		
		return sql;
	}
	
	public static String getModel(){
		String sql = "select name from models " +
						"where name = ? ";
		
		return sql;
	}
	
	public static String createModel(String modelName, String columns){
		String sql = "create table " + modelName +
						" (" +
						"id int not null auto_increment," +
						columns +
						"primary key(id)" +
						")";
		
		return sql;
	}
	
	public static String deleteModel(){
		String sql = "delete from models where name = ?";
		
		return sql;
	}


		
}

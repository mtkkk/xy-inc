package br.com.testezup.sql;

public class Statements {

	
	/*
	 * Statements MODELS
	 */
	public static String insertModel(){
		String sql = "insert into models (name,datecreation) values (?,sysdate())";
		
		return sql;
	}
	
	public static String getModels(){
		String sql = "select name from models";
		
		return sql;
	}
	
	public static String deleteModel(){
		String sql = "delete from models where name = ?";
		
		return sql;
	}
		
}

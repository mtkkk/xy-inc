package br.com.testezup.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.testezup.dao.DynamicModelDAO;
import br.com.testezup.models.DynamicModelEntry;
import br.com.testezup.models.Model;

public class DynamicModelService {

	public JSONArray getDynamicModels(String modelName){
		try{
			return new DynamicModelDAO().getDynamicModels(modelName);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public JSONObject getDynamicModel(String modelName, String id) throws Exception{
		try{
			return new DynamicModelDAO().getDynamicModel(modelName,id);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public String createDynamicModelEntry(DynamicModelEntry entry,String modelName) throws Exception{
		try{
			DynamicModelDAO dao = new DynamicModelDAO();
			String columns, values;
			
			Map<String,String> attributes = dao.getAttributes(modelName);
			
			columns = setTableColumns(entry,attributes);
			values = setValuesString(attributes);
			
			if(columns.length() > 0){
				dao.createDynamicModelEntry(modelName,columns,values,attributes,entry);
			}
			
			return entry.get(dao.getPrimaryKey(modelName)).toString();				
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	//Monta as colunas que serão inseridas na tabela do modelo
	public String setTableColumns(DynamicModelEntry entry, Map<String,String> attributes){
		StringBuilder str = new StringBuilder();
		boolean first = true;
		
		//Verifica se os campos passados existem para esta tabela/modelo
		for(Entry<String,String> e : entry.entrySet()){
			if(attributes.containsKey(e.getKey())){
				if(first){
					str.append(e.getKey());
					first = false;
				} else{
					str.append("," + e.getKey());
				}				
			}
		}
		
		return str.toString();
	}
	
	public String setValuesString(Map<String,String> attributes){
		StringBuilder str = new StringBuilder();
		boolean first = true;
		
		str.append(" (");
		
		for(Entry<String,String> entry : attributes.entrySet()){
			if(first){
				str.append("?");
				first = false;
			} else {
				str.append(",?");
			}
		}
		
		str.append(") ");
		
		return str.toString();
	}
}

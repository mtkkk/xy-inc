package br.com.testezup.services;

import java.util.Map;
import java.util.Map.Entry;

import br.com.testezup.dao.ModelDAO;
import br.com.testezup.models.Model;

public class ModelService {

	//Cria a tabela a partir do modelo fornecido, guardando também um registro das informações passadas
	public void createModel(Model model){
		ModelDAO dao = new ModelDAO();				
		
		String columns = setTableColumns(model);
		if(columns.length() > 0){
			dao.createNewModel(model.getModelName(),columns);
		}		
		
		dao.insertNewModel(model.getModelName());
		for(Entry<String,String> entry : model.getAttributes().entrySet()){
			dao.insertNewModelAttributes(model.getModelName(),entry.getKey().toLowerCase(),entry.getValue().toLowerCase());
		}
		
	}
	
	//Monta a string contendo quais colunas serão criadas para a tabela
	public String setTableColumns(Model model){
		StringBuilder str = new StringBuilder();
		
		for(Entry<String,String> entry : model.getAttributes().entrySet()){
			switch(entry.getValue().toLowerCase()){
			
			case "string":
				str.append(entry.getKey().toLowerCase() + " varchar(250),");
				break;
			case "int":
				str.append(entry.getKey().toLowerCase() + " int,");
				break;
			case "decimal":
				str.append(entry.getKey().toLowerCase() + " double,");
				break;
			}
		}
		
		return str.toString();
	}
}

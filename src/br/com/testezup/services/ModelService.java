package br.com.testezup.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ValidationLevel;
import com.mongodb.client.model.ValidationOptions;

import br.com.testezup.dao.ModelDAO;
import br.com.testezup.models.Model;

public class ModelService {

	public List<Document> getModels(){
		try{
			return new ModelDAO().getModelsMongo();
		} catch(Exception ex){
			throw ex;
		}
	}
	
	public Document getModelMongo(String id) {
		try{
			return new ModelDAO().getModelMongo(id);
		} catch(Exception ex){
			throw ex;
		}		
	}
	
	//M�todo para criar um novo model e tamb�m criar a collection deste model no MongoDB
	public void createModelMongo(Model model){
		try{
			ModelDAO dao = new ModelDAO();
			Document doc = prepareNewDocument(model);
			
			dao.createNewModelMongo(doc,createValidator(model.getAttributes()));
			
		} catch(Exception ex){
			throw ex;
		}
	}
	//Cria a tabela a partir do modelo fornecido, guardando tamb�m um registro das informa��es passadas
	public void createModel(Model model) throws Exception{
		try{
			if(model.getPrimarykey() == null) {
				throw new Exception("A Primary Key deve ser informada");
			}
			ModelDAO dao = new ModelDAO();				
			
			String columns = setTableColumns(model);
			if(columns.length() > 0){
				dao.createNewModel(model.getModelName().toLowerCase(),columns,model.getPrimarykey());
			}		
			
			dao.insertNewModel(model);
			for(Entry<String,String> entry : model.getAttributes().entrySet()){
				dao.insertNewModelAttributes(model.getModelName(),entry.getKey().toLowerCase(),entry.getValue().toLowerCase());
			}
		} catch (Exception ex) {
			throw ex;
		}					
	}
	
	public void deleteModel(String id) {
		try{
			new ModelDAO().deleteModel(id);
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public void deleteModelMongo(String id) {		
		try{
			new ModelDAO().deleteModelMongo(id);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	//Monta a string contendo quais colunas ser�o criadas para a tabela e seus tipos
	public String setTableColumns(Model model) throws Exception{
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
				str.append(entry.getKey().toLowerCase() + " decimal,");
				break;
			case "double":
				str.append(entry.getKey().toLowerCase() + " double,");
				break;
			case "float":
				str.append(entry.getKey().toLowerCase() + " float,");
				break;
			case "date":
				str.append(entry.getKey().toLowerCase() + " date,");
				break;
			case "char":
				str.append(entry.getKey().toLowerCase() + " char,");
				break;
			default:
				throw new Exception("Tipo de dado n�o suportado");
			}
		}
		
		return str.toString();
	}
	
	public Document prepareNewDocument(Model model){
		Document doc = new Document("name",model.getModelName().toLowerCase());
		
		if(model.getAttributes().size() > 0){			
			doc.append("attributes", model.getAttributes());			
		} else {
			throw new RuntimeException("O modelo que est� tentando criar n�o possui nenhum atributo, verifique a sintaxe e tente novamente");
		}
		
		return doc;
	}
	
	//Cria o validator que ser� usado para validar o tipo de dado para cada campo de acordo com os tipos passados na cria��o do modelo
	public ValidationOptions createValidator(Map<String,String> attributes){
		ValidationOptions options = new ValidationOptions();
		List<Bson> rules = new ArrayList<Bson>();
		
		for(Entry<String,String> entry : attributes.entrySet()){
			rules.add(Filters.type(entry.getKey().toLowerCase(), entry.getValue().toLowerCase()));
		}
		
		options.validator(Filters.and(rules));
		
		return options; 	
	}	
	
}

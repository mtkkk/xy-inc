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
	
	private ModelDAO dao;
	
	public ModelService(){
		this.dao = new ModelDAO();
	}

	public List<Document> getModels(){
		try{
			return dao.getModelsMongo();
		} catch(Exception ex){
			throw ex;
		}
	}
	
	public Document getModelMongo(String id) {
		try{
			return dao.getModelMongo(id);
		} catch(Exception ex){
			throw ex;
		}		
	}
	
	//Método para criar um novo model e também criar a collection deste model no MongoDB
	public void createModelMongo(Model model){
		try{
			Document doc = prepareNewDocument(model);
			
			dao.createNewModelMongo(doc,createValidator(model.getAttributes()),getUniqueFields(model));			
		} catch(Exception ex){
			throw ex;
		}
	}
	//Cria a tabela a partir do modelo fornecido, guardando também um registro das informações passadas
	public void createModel(Model model) throws Exception{
		try{
			if(model.getPrimarykey() == null) {
				throw new Exception("A Primary Key deve ser informada");
			}			
			
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
			dao.deleteModel(id);
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public void deleteModelMongo(String id) {		
		try{
			dao.deleteModelMongo(id);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	//Monta a string contendo quais colunas serão criadas para a tabela e seus tipos
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
				throw new Exception("Tipo de dado não suportado");
			}
		}
		
		return str.toString();
	}
	
	//Cria o documento que será utilizado para inserir uma nova entrada no banco
	public Document prepareNewDocument(Model model){
		Document doc = new Document("name",model.getModelName().toLowerCase());
		
		if(model.getAttributes().size() > 0){			
			doc.append("attributes", model.getAttributes());			
		} else {
			throw new IllegalArgumentException("O modelo que está tentando criar não possui nenhum atributo, verifique a sintaxe e tente novamente");
		}
		
		if(model.getRules() != null){
			doc.append("rules", model.getRules());
		} else {
			throw new IllegalArgumentException("Nenhuma regra foi encontrada, por favor informe os campos obrigatórios");
		}
				
		return doc;
	}
	
	//Cria o validator que será usado para validar o tipo de dado para cada campo de acordo com os tipos passados na criação do modelo
	public ValidationOptions createValidator(Map<String,String> attributes){
		ValidationOptions options = new ValidationOptions();
		List<Bson> rules = new ArrayList<Bson>();
		
		for(Entry<String,String> entry : attributes.entrySet()){
			rules.add(Filters.type(entry.getKey().toLowerCase(), entry.getValue().toLowerCase()));
		}
		
		options.validator(Filters.and(rules));
		
		return options; 	
	}
	
	//Checa se existem regras para campos únicos e os retorna como lista (junto com o identifier)
	public List<String> getUniqueFields(Model model){		
		List<String> fields = new ArrayList<String>();
		if(getIdentifier(model) != null){
			fields.add(getIdentifier(model));
		}
		if(model.getRules().containsKey("unique")){
			fields.addAll((List<String>) model.getRules().get("unique"));
		}
		
		if(fields.size() <= 0) {
			throw new IllegalArgumentException("Nenhuma regra foi encontrada, por favor informe os campos obrigatórios");
		}
		return fields;
	}
	
	//Pega o campo utilizado como identifier do modelo
	public String getIdentifier(Model model){
		if(model.getRules().containsKey("identifier")){
			return model.getRules().get("identifier").toString();
		} else {
			return null;
		}
	}
	
}

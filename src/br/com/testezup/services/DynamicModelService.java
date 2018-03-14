package br.com.testezup.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import br.com.testezup.dao.DynamicModelDAO;
import br.com.testezup.dao.ModelDAO;
import br.com.testezup.models.DynamicModelEntry;
import br.com.testezup.models.Model;
import br.com.testezup.models.MongoEntry;

public class DynamicModelService {

	private DynamicModelDAO dao;
	private ModelDAO mDao;
	private Document model;
	
	public DynamicModelService(){
		this.mDao = new ModelDAO();
		this.dao = new DynamicModelDAO(this.mDao.getDb());		
	}
	
	//Ao inicializar o serviço já setta modelo sendo trabalhado
	public DynamicModelService(String modelName){
		this.mDao = new ModelDAO();
		this.dao = new DynamicModelDAO(this.mDao.getDb());
		this.model = mDao.getModelMongo(modelName);
		Map<String,Object> attributes = (Map<String, Object>) this.model.get("attributes");
		
	}
	
	public JSONArray getDynamicModels(String modelName){
		try{
			return dao.getDynamicModels(modelName);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public List<Document> getDynamicModelsMongo(String modelName){
		try{			
			return dao.getDynamicModelsMongo(modelName);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public JSONObject getDynamicModel(String modelName, String id) throws Exception{
		try{			
			return dao.getDynamicModel(modelName,id);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	//Procura qual o identifier deste modelo e envia para buscar 
	//Em uma evolução do sistema é possível fazer query string params para buscar por outros campos 
	public Document getDynamicModelMongo(String modelName, String id){
		try{
			String identifier = mDao.getModelIdentifier(modelName);
			return dao.getDynamicModelMongo(modelName, id, identifier);
		} catch(Exception ex) {
			throw ex;
		}
	}
	
	public String createDynamicModelEntry(DynamicModelEntry dmEntry,String modelName) throws Exception{
		try{			
			String columns, values;
			
			Map<String,String> attributes = dao.getAttributes(modelName);
			
			columns = setTableColumns(dmEntry,attributes);
			values = setValuesString(attributes);
			
			if(columns.length() > 0){
				dao.createDynamicModelEntry(modelName,columns,values,attributes,dmEntry);
			} else{
				throw new Exception("Modelo não pôde ser encontrado");
			}
			
			return dmEntry.get(dao.getPrimaryKey(modelName)).toString();				
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public String createDynamicModelEntryMongo(MongoEntry entry, String modelName) throws Exception {
		try{						
			Document doc = prepareNewDocument(entry, modelName);
			
			dao.createDynamicModelEntryMongo(doc,modelName);
			//TODO -> RETORNO
			return "";
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public void updateDynamicModelEntry(DynamicModelEntry dmEntry, String modelName, String id) throws Exception {		
		try{
			String columns;
			
			Map<String,String> attributes = dao.getAttributes(modelName);
			
			columns = setTableColumnsForUpdate(dmEntry,attributes);
			
			if(columns.length() > 0){
				dao.updateDynamicModelEntry(modelName,columns,attributes,dmEntry,id);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}	
	
	public void updateDynamicModelEntryMongo(MongoEntry dmEntry, String modelName, String id) throws Exception {
		try{
			Document doc = prepareNewDocument(dmEntry, modelName);
			String identifier = mDao.getModelIdentifier(modelName);
			
			Bson updateOperation = new Document("$set",doc);
			
			dao.updateDynamicModelEntryMongo(updateOperation,modelName,id,identifier);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public void deleteDynamicModelEntry(String modelName, String id) throws Exception {
		try{
			dao.deleteDynamicModelEntry(modelName,id);
		} catch (Exception ex) {
			throw ex;
		}		
	}
	
	public void deleteDynamicModelEntryMongo(String modelName, String id) {
		try{
			String identifier = mDao.getModelIdentifier(modelName);
			dao.deleteDynamicModelEntryMongo(modelName,id,identifier);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	//Monta as colunas que serão inseridas na tabela do modelo
	public String setTableColumns(DynamicModelEntry dmEntry, Map<String,String> attributes){
		StringBuilder str = new StringBuilder();
		boolean first = true;
		
		//Verifica se os campos passados existem para esta tabela/modelo
		for(Entry<String,String> e : dmEntry.entrySet()){
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
	
	public String setTableColumnsForUpdate(DynamicModelEntry dmEntry, Map<String,String> attributes){
		StringBuilder str = new StringBuilder();
		boolean first = true;
		
		//Verifica se os campos passados existem para esta tabela/modelo
		for(Entry<String,String> e : dmEntry.entrySet()){
			if(attributes.containsKey(e.getKey())){
				if(first){
					str.append(e.getKey() + " = ?");
					first = false;
				} else{
					str.append("," + e.getKey() + " = ?");
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
	
	public Document prepareNewDocument(MongoEntry entry, String modelName) throws ParseException{
		Document doc = new Document();
		
		for(Entry<String,Object> e : entry.entrySet()){
			if((e.getKey()).toLowerCase() == "date"){ //TODO -> Pegar o tipo
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(e.getValue().toString());
				doc.append(e.getKey(), date);
			} else {
				doc.append(e.getKey(), e.getValue());
			}			
		}		
		return doc;
	}
}

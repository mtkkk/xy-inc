package br.com.testezup.models;

import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;

public class Model {

	private String modelName;
	private Map<String,String> attributes;
	private String primarykey;
	private Map<String,Object> rules;
	
	public Model(){}
	
	public Model(Document doc){
		this.modelName = doc.getString("name");
		this.attributes = (Map<String, String>) doc.get("attributes");
		this.rules = (Map<String, Object>) doc.get("rules");
	}
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String getPrimarykey() {
		return primarykey;
	}
	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}
	public Map<String, Object> getRules() {
		return rules;
	}
	public void setRules(Map<String, Object> rules) {
		this.rules = rules;
	}	
}

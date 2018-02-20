package br.com.testezup.models;

import java.util.Map;

public class Model {

	private String modelName;
	private Map<String,String> attributes;
	
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

	
}

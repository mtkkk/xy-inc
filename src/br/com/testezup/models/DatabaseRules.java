package br.com.testezup.models;

import java.util.List;

public class DatabaseRules {

	private String identifier;
	private List<String> unique;
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public List<String> getUnique() {
		return unique;
	}
	public void setUnique(List<String> unique) {
		this.unique = unique;
	}
	
	
}

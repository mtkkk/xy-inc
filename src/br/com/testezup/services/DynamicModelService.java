package br.com.testezup.services;

import java.util.List;

import org.json.JSONArray;

import br.com.testezup.dao.DynamicModelDAO;

public class DynamicModelService {

	public JSONArray getDynamicModels(String modelName){
		try{
			return new DynamicModelDAO().getDynamicModels(modelName);
		} catch (Exception ex) {
			throw ex;
		}
	}
}

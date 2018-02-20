package br.com.testezup.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.testezup.dao.DynamicModelDAO;
import br.com.testezup.dao.ModelDAO;
import br.com.testezup.models.Model;
import br.com.testezup.services.DynamicModelService;
import br.com.testezup.services.ModelService;

@Path("models/{modelName}")
public class DynamicModelResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDynamicModels(@PathParam("modelName") String modelName){
		try {		
			
			JSONArray data = new DynamicModelService().getDynamicModels(modelName);
			
			return data.toString();
		} catch (Exception ex) {
			return "[{\"status\":\"erro\",\"mensagem\":\"" + ex.getMessage() + "\"}]";
		}
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDynamicModel(@PathParam("modelName") String modelName, @PathParam("id") String id){
		try {
			String str = modelName;
			return new Gson().toJson(str);
		} catch (Exception ex) {
			return "[{\"status\":\"erro\",\"mensagem\":\"" + ex.getMessage() + "\"}]";
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDynamicModel(String body){
		try{
			Gson gson = new GsonBuilder().create();
			Model model = gson.fromJson(body, Model.class);		
			
			new ModelService().createModel(model);
			
			URI uri = URI.create("/xy-inc/api/models/" + model.getModelName());
			return Response.created(uri).build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
	
	
}

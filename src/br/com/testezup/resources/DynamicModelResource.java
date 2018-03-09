package br.com.testezup.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import br.com.testezup.models.DynamicModelEntry;
import br.com.testezup.models.Model;
import br.com.testezup.services.DynamicModelService;
import br.com.testezup.services.ModelService;

@Path("v1/models/baas/{modelName}")
public class DynamicModelResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDynamicModelsEntries(@PathParam("modelName") String modelName){
		try {		
			
			JSONArray dmEntries = new DynamicModelService().getDynamicModels(modelName);
			
			return dmEntries.toString();
		} catch (Exception ex) {
			JSONObject jObj = new JSONObject();
			jObj.put("status", "error");
			jObj.put("message:", ex.getMessage());
			return jObj.toString();
		}
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDynamicModelEntry(@PathParam("modelName") String modelName, @PathParam("id") String id){
		try {
			
			JSONObject dmEntry = new DynamicModelService().getDynamicModel(modelName,id);
			
			return dmEntry.toString();
		} catch (Exception ex) {
			JSONObject jObj = new JSONObject();
			jObj.put("status", "error");
			jObj.put("message:", ex.getMessage());
			return jObj.toString();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDynamicModelEntry(String body, @PathParam("modelName") String modelName){
		try{
			Gson gson = new GsonBuilder().create();
			DynamicModelEntry dmEntry = gson.fromJson(body, DynamicModelEntry.class);		
			
			String entryPath = new DynamicModelService().createDynamicModelEntry(dmEntry,modelName);
			
			URI uri = URI.create("/xy-inc/api/v1/models/baas/" + modelName + "/" + entryPath);
			return Response.created(uri).build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDynamicModelEntry(String body, @PathParam("modelName") String modelName, @PathParam("id") String id){
		try{
			Gson gson = new GsonBuilder().create();
			DynamicModelEntry dmEntry = gson.fromJson(body, DynamicModelEntry.class);
			
			new DynamicModelService().updateDynamicModelEntry(dmEntry,modelName,id);
			
			return Response.ok().build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteDynamicModelEntry(@PathParam("modelName") String modelName, @PathParam("id") String id){
		try{
			
			new DynamicModelService().deleteDynamicModelEntry(modelName,id);
			
			return Response.ok().build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
	
	
}

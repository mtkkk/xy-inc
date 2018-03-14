package br.com.testezup.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.bson.Document;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.testezup.models.MongoEntry;
import br.com.testezup.models.errors.DefaultResponseError;
import br.com.testezup.services.DynamicModelService;

@Path("v2/models/baas/{modelName}")
public class DynamicModelResourceV2 {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDynamicModelsEntries(@PathParam("modelName") String modelName){
		try {		
			
			List<Document> dmEntries = new DynamicModelService().getDynamicModelsMongo(modelName.toLowerCase());
			
			return new Gson().toJson(dmEntries);
		} catch (Exception ex) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();			
			return gson.toJson(new DefaultResponseError(ex));
		}
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDynamicModelEntry(@PathParam("modelName") String modelName, @PathParam("id") String id){
		try {
			
			Document dmEntry = new DynamicModelService().getDynamicModelMongo(modelName.toLowerCase(),id);
			
			return new Gson().toJson(dmEntry);
		} catch (Exception ex) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();			
			return gson.toJson(new DefaultResponseError(ex));
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDynamicModelEntry(String body, @PathParam("modelName") String modelName){
		try{
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			MongoEntry dmEntry = gson.fromJson(body, MongoEntry.class);
			modelName = modelName.toLowerCase();
			
			String entryPath = new DynamicModelService(modelName).createDynamicModelEntryMongo(dmEntry,modelName);
			
			URI uri = URI.create("/xy-inc/api/v2/models/baas/" + modelName + "/" + entryPath);
			return Response.created(uri).build();
		} catch	(Exception ex){			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return Response.serverError().entity(gson.toJson(new DefaultResponseError(ex))).build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDynamicModelEntry(String body, @PathParam("modelName") String modelName, @PathParam("id") String id){
		try{
			Gson gson = new GsonBuilder().create();
			MongoEntry dmEntry = gson.fromJson(body, MongoEntry.class);
			modelName = modelName.toLowerCase();
			
			new DynamicModelService(modelName).updateDynamicModelEntryMongo(dmEntry,modelName,id);
			
			return Response.ok().build();
		} catch	(Exception ex){			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return Response.serverError().entity(gson.toJson(new DefaultResponseError(ex))).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteDynamicModelEntry(@PathParam("modelName") String modelName, @PathParam("id") String id){
		try{
			
			new DynamicModelService().deleteDynamicModelEntryMongo(modelName.toLowerCase(),id);
			
			return Response.ok().build();
		} catch	(Exception ex){			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return Response.serverError().entity(gson.toJson(new DefaultResponseError(ex))).build();
		}
	}
}

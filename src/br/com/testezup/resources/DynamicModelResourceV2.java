package br.com.testezup.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.testezup.services.DynamicModelService;

@Path("v2/models/baas/{modelName}")
public class DynamicModelResourceV2 {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDynamicModelEntry(String body, @PathParam("modelName") String modelName){
		try{
			Gson gson = new GsonBuilder().create();
			Map<String,Object> dmEntry = gson.fromJson(body, HashMap.class);		
			
			String entryPath = new DynamicModelService().createDynamicModelEntryMongo(dmEntry,modelName.toLowerCase());
			
			URI uri = URI.create("/xy-inc/api/models/baas/" + modelName + "/" + entryPath);
			return Response.created(uri).build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
}

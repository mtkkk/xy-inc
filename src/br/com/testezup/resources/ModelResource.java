package br.com.testezup.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.testezup.dao.ModelDAO;
import br.com.testezup.models.Model;
import br.com.testezup.services.ModelService;

@Path("v1/models")
public class ModelResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getModels(){
		try {
			List<String> models = new ModelDAO().getModels();
			return new Gson().toJson(models);
		} catch (Exception ex) {
			return "[{\"status\":\"erro\",\"mensagem\":\"" + ex.getMessage() + "\"}]";
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{modelName}")
	public String getModel(@PathParam("modelName") String modelName){
		try {
			Model model = new ModelDAO().getModel(modelName.toLowerCase());
			return new Gson().toJson(model);
		} catch (Exception ex) {
			return "[{\"status\":\"erro\",\"mensagem\":\"" + ex.getMessage() + "\"}]";
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createModel(String body){
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
	
	@DELETE
	@Path("{id}")
	public Response deleteModel(@PathParam("id") String id){
		try{				
			
			new ModelService().deleteModel(id.toLowerCase());
						
			return Response.ok().build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
		
}

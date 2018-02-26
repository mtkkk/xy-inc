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

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.testezup.dao.ModelDAO;
import br.com.testezup.models.Model;
import br.com.testezup.services.ModelService;

@Path("v2/models")
public class ModelResourceV2 {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getModels(){
		try {
			List<Document> models = new ModelService().getModels();
			return new Gson().toJson(models);
		} catch (Exception ex) {
			return "[{\"status\":\"erro\",\"mensagem\":\"" + ex.getMessage() + "\"}]";
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String getModel(@PathParam("id") String id){
		try {
			Document doc = new ModelService().getModelMongo(id.toLowerCase());
			return new Gson().toJson(doc);
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
			
			new ModelService().createModelMongo(model);
			
			URI uri = URI.create("/xy-inc/api/v2/models/" + model.getModelName().toLowerCase());
			return Response.created(uri).build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteModel(@PathParam("id") String id){
		try{							
			new ModelService().deleteModelMongo(id.toLowerCase());
						
			return Response.ok().build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
	
}

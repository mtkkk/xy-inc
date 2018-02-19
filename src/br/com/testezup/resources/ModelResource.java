package br.com.testezup.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.testezup.models.Model;

@Path("models")
public class ModelResource {

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createModel(String body){
		try{
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Model model = gson.fromJson(body, Model.class);
			int modelId;						
		
			
			URI uri = URI.create("/xy-inc/api/contas/" + modelId);
			return Response.created(uri).build();
		} catch	(Exception ex){			
			return Response.serverError().entity(ex.getCause().getMessage()).build();
		}
	}
}

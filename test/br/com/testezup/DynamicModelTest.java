package br.com.testezup;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DynamicModelTest {
	
	private final String hostname = "http://localhost:8080";

	@Test
	public void t1_testsDynamicModelEntryCreation(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);		
	
		JSONObject jObj = new JSONObject();
		
		jObj.put("key1", "admin");
		jObj.put("key2", 10);				
		
		String json = jObj.toString();
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/xy-inc/api/models/baas/adminunittest/").request().post(entity);
		
		Assert.assertEquals(201, response.getStatus());
	}
	
	@Test
	public void t2_testsGettingSpecificDynamicModel(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);
		String response = target.path("/xy-inc/api/models/baas/adminunittest/admin").request().get(String.class);
		Assert.assertTrue(response.contains("admin"));
		System.out.println("Teste2: t2_testsGettingSpecificDynamicModel");
		System.out.println(response);
	}
	
	@Test
	public void t3_testsGettingAllDynamicModels(){
			
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);
		String response = target.path("/xy-inc/api/models/baas/adminunittest/").request().get(String.class);		
		Assert.assertTrue(response.contains("admin"));
		System.out.println("Teste3: t3_testsGettingAllDynamicModels");
		System.out.println(response);
		
	}
	
	@Test
	public void t4_testsUpdatingDynamicModelEntry(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);		
	
		JSONObject jObj = new JSONObject();
		
		jObj.put("key2", 25);				
		
		String json = jObj.toString();
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/xy-inc/api/models/baas/adminunittest/admin").request().put(entity);
		
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void t5_testsDeletingDynamicModelEntry(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);
		Response response = target.path("/xy-inc/api/models/baas/adminunittest/admin").request().delete();		
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void t6_testsDynamicModelEntryCreationWithWrongDataType(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);		
	
		JSONObject jObj = new JSONObject();
		
		jObj.put("key1", "admin");
		jObj.put("key2", "TipoErrado");				
		
		String json = jObj.toString();
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/xy-inc/api/models/baas/adminunittest/").request().post(entity);
		
		Assert.assertEquals(500, response.getStatus());
	}
	
}

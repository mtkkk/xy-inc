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
public class ModelTest {	

	private final String hostname = "http://localhost:8080";	
	
	@Test
	public void t1_testsModelCreation(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);		
	
		JSONObject jObj = new JSONObject();
		JSONObject jAttr = new JSONObject();
		
		jAttr.put("key1", "String");
		jAttr.put("key2", "Int");
		jAttr.put("key3", "Date");
		jAttr.put("key4", "Double");
		
		jObj.put("modelName", "TestTable");
		jObj.put("attributes", jAttr);
		jObj.put("primarykey", "key1");
		
		
		String json = jObj.toString();
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/xy-inc/api/models").request().post(entity);
		
		Assert.assertEquals(201, response.getStatus());
	}
	
	@Test
	public void t2_testsGettingSpecificModel(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);
		String response = target.path("/xy-inc/api/models/testtable").request().get(String.class);
		Assert.assertTrue(response.contains("testtable"));
	}
	
	@Test
	public void t3_testsGettingAllModels(){
			
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);
		String response = target.path("/xy-inc/api/models").request().get(String.class);		
		Assert.assertTrue(response.contains("testtable"));
		
	}
	
	@Test
	public void t4_testsDeletingAModel(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);
		Response response = target.path("/xy-inc/api/models/testtable").request().delete();		
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void t5_testsModelCreationWithoutPrimaryKey(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);		
	
		JSONObject jObj = new JSONObject();
		JSONObject jAttr = new JSONObject();
		
		jAttr.put("key1", "String");
		jAttr.put("key2", "Int");
		jAttr.put("key3", "Date");
		jAttr.put("key4", "Double");
		
		jObj.put("modelName", "TestTable");
		jObj.put("attributes", jAttr);
		
		
		String json = jObj.toString();
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/xy-inc/api/models").request().post(entity);
		
		Assert.assertEquals(500, response.getStatus());
	}
	
	@Test
	public void t6_testsModelCreationWithWrongBodyFormat(){
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);		
		
		
		String json = "{ 123 }";
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/xy-inc/api/models").request().post(entity);
		
		Assert.assertEquals(500, response.getStatus());
	}
	
	@Test
	public void t7_testsModelCreationWithWrongDataType(){
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname);		
	
		JSONObject jObj = new JSONObject();
		JSONObject jAttr = new JSONObject();
		
		jAttr.put("key1", "String");
		jAttr.put("key2", "Int");
		jAttr.put("key3", "Date");
		jAttr.put("key4", "TipoNaoSuportado");
		
		jObj.put("modelName", "TestTable");
		jObj.put("attributes", jAttr);
		jObj.put("primarykey", "key1");
		
		
		String json = jObj.toString();
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/xy-inc/api/models").request().post(entity);
		
		Assert.assertEquals(500, response.getStatus());
	}
}

package br.com.testezup.services;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class RESTservice extends ResourceConfig {

	public RESTservice(){
		packages("com.fasterxml.jackson.jaxrs.json");
        packages("br.com.testezup.resources");
	}
}
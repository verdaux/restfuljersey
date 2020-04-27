package com.sample.restclient;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

public class ClientTest {
  public static void main(String[] args) {
    //ClientConfig config = new ClientConfig();
	/*
	 * Client client = ClientBuilder.newClient(config); WebTarget target =
	 * client.target(getBaseURI());
	 * 
	 * System.out.println(target.path("rest").path("hello").request().accept(
	 * MediaType.TEXT_PLAIN).get(String.class));
	 * System.out.println(target.path("rest").path("hello").request().accept(
	 * MediaType.TEXT_XML).get(String.class));
	 * System.out.println(target.path("rest").path("hello").request().accept(
	 * MediaType.TEXT_HTML).get(String.class));
	 */
  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:8080/restfuljersey").build();
  }
} 
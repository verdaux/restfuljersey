package com.sample.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sample.rest.model.Message;
import com.sample.rest.serviceController.MessageService;

@Path("/messages")
public class MessageResource
{
	MessageService messageService = new MessageService();
	
	@GET
	@Path("/getAllmessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessages()
	{
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/getAllmessagesinXML")
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getAllMessagesinXML()
	{
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessages()
	{
		return "Hello World";
		
	}
}

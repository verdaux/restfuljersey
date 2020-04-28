package com.sample.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sample.rest.dao.MessageDAO;
import com.sample.rest.model.Message;
import com.sample.rest.serviceController.MessageService;

@Path("/messages")
public class MessageResource
{
	MessageService messageService = new MessageService();
	MessageDAO messageDAO = new MessageDAO();
	
	@GET
	@Path("/getAllmessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessages()
	{
		return messageService.getAllMessages();
	}


	@POST
	@Path("addMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	private Response  addMessage(Message message)
	{
		messageDAO.addMessage(message);
		return Response.ok().build();
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

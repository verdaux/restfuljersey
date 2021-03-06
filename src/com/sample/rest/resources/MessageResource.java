package com.sample.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sample.rest.auth.Secured;
import com.sample.rest.dao.MessageDAO;
import com.sample.rest.model.Message;
import com.sample.rest.serviceController.MessageService;

@Path("/messages")
public class MessageResource
{
	MessageService messageService = new MessageService();
	MessageDAO messageDAO = new MessageDAO();
	
	@Secured
	@GET
	@Path("/getAllmessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessages()
	{
		MessageDAO dao = new MessageDAO();
		return dao.getMessages();
	}

	@GET
    @Path("/getMessage/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public String getMessage(@PathParam("id") int id)
	{
		MessageDAO dao = new MessageDAO();
		String msg = dao.getMessage(id);
        if(msg==null || msg.equals("") || msg.length() ==0)
        {
            return "No message";
        }
        return msg;
    }

	@POST
	@Path("/addMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response  addMessage(Message message)
	{
		message.setMessage(message.getMessage());
		message.setCreatedOn(message.getCreatedOn());
		message.setAmount(message.getAmount());
        
        MessageDAO dao = new MessageDAO();
        dao.addMessage(message);
        
        return Response.ok().build();
	}
	
	@PUT
    @Path("/update/{id}")
    @Consumes("application/json")
    public Response updateMessage(@PathParam("id") int id, Message message)
	{
		MessageDAO dao = new MessageDAO();
        int count = dao.updateMessage(id, message);
        if(count==0)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Consumes("application/json")
    public Response deleteEmployee(@PathParam("id") int id)
    {
    	MessageDAO dao = new MessageDAO();
        int count = dao.deleteMessage(id);
        if(count==0)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
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

package com.sample.rest.serviceController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.sample.rest.model.Message;

public class MessageService
{
	public List<Message> getAllMessages()
	{
		Message m1 = new Message(1, "Hi", new Date(), 58.1);
		Message m2 = new Message(2, "Hello", new Date(), 100.0);
		Message m3 = new Message(3, "Hey", new Date(), 99.99);
		
		List<Message> messageList = Arrays.asList(m1,m2,m3);
		System.out.println("message:: "+messageList);
		
		return messageList;
	}
	
	public void addMessage(Message message)
	{
		
	}
	
	public static void main(String[] args)
	{
		MessageService ms = new MessageService();
		ms.getAllMessages();
	}
}

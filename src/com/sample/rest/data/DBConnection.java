package com.sample.rest.data;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sample.rest.model.Message;

public class DBConnection
{

	public static void main(String[] args)
	{
		Message message = new Message();
		
		message.setId(1L);
		message.setMessage("First message");
		message.setCreatedOn(new Date());
		message.setAmount(100.0);
		
		try
		{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(message);
		session.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

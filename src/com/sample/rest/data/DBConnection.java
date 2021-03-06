package com.sample.rest.data;

import java.sql.CallableStatement;
import java.util.Date;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.rest.dao.MessageDAO;
import com.sample.rest.model.Message;
import com.sample.rest.util.Constants;



public class DBConnection
{
	static Message messages = new Message();

	public static void main(String[] args)
	{
		Message message = new Message();
		
		
		 // message.setId(1L);
		/*
		 * message.setMessage("First message"); message.setCreatedOn(new Date());
		 * message.setAmount(100.0);
		 */
		 
		
		try
		{
		//	System.out.println("title:: "+Constants.title);
		//	System.out.println("proc:: "+Constants.procDB);
			Constants.EXCEPTION_LOGGER.error(Constants.title);
			Constants.COMMON_LOGGER.debug(Constants.title);
			MessageDAO dao = new MessageDAO();
			//dao.getMessage(1);
			/*
			 * SessionFactory sessionFactory = new
			 * Configuration().configure().buildSessionFactory(); Session session =
			 * sessionFactory.openSession(); session.beginTransaction();
			 * session.save(message); session.getTransaction().commit();
			 */
			 
		
		//callProcs();
			//enhancedCallProc();
		}
	
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void callProcs()
	{
		// Creating the configuration instance & passing the hibernate configuration file.
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
 
        // Hibernate session object to start the db transaction.
        Session s = config.buildSessionFactory().openSession();
 
        // Fetching the data from the database using stored procedure queries.
 
        // Stored procedure query #1
        System.out.println(":::: Find all messages ::::");
 
        StoredProcedureQuery allemployees = (StoredProcedureQuery) s.createStoredProcedureCall("getAllMessages", Message.class);
 
        List<Message> elist = (List<Message>) allemployees.getResultList();
 
        for(Message messages : elist)
        {
            System.out.println(messages.toString());
        }
        s.close();
        
        
	}
	public static void enhancedCallProc()
	{
		// Creating the configuration instance & passing the hibernate configuration file.
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
 
        // Hibernate session object to start the db transaction.
        Session s = config.buildSessionFactory().openSession();
		System.out.println("\n:::: Find employee count by designation ::::");
		 
        StoredProcedureQuery count = (StoredProcedureQuery) s.createStoredProcedureCall(Constants.procDB);
        count.registerStoredProcedureParameter("idVal", Integer.class, ParameterMode.IN);
        count.registerStoredProcedureParameter("idCount", Integer.class, ParameterMode.OUT);
 
        Integer param = 1;
        count.setParameter("idVal", param);
        count.execute();
 
        Integer idCount = (Integer) count.getOutputParameterValue("idCount");
        System.out.println("Message count for designation= " + param + " is= " + idCount);
 
        // Closing the session object.
        s.close();
	}
	
	@SuppressWarnings("unchecked")
	public static  void latestCall()
	{
		/*
		 * ProcedureCall procedureCall =
		 * SessionUtil.getSession().createStoredProcedureCall("find_count_by_id");
		 * procedureCall.registerParameter("idVal", Long.class, ParameterMode.IN);
		 * //procedureCall.getParameterRegistration("idCount").bindValue(messages.getId(
		 * ));
		 * //procedureCall.getParameterRegistration("COMPANY").bindValue(company.getId()
		 * ); procedureCall.setParameter("idVal", 1); ProcedureOutputs procedureOutputs
		 * = procedureCall.getOutputs(); ResultSetOutput resultSetOutput =
		 * (ResultSetOutput) procedureOutputs.getCurrent();
		 * 
		 * @SuppressWarnings("rawtypes") List results = resultSetOutput.getResultList();
		 * for(Integer i=0;i<results.size();i++) { Object[] objects = (Object[])
		 * results.get(i); System.out.println(objects[i]);
		 * //LOGGER.info("The benefit is "+objects[1]); }
		 */
	}
}

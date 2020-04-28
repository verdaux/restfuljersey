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

import com.sample.rest.model.Message;

public class DBConnection
{
	Message messages = new Message();

	public static void main(String[] args)
	{
		Message message = new Message();
		
		/*
		 * message.setId(1L); message.setMessage("First message");
		 * message.setCreatedOn(new Date()); message.setAmount(100.0);
		 */
		
		try
		{
			/*
			 * SessionFactory sessionFactory = new
			 * Configuration().configure().buildSessionFactory(); Session session =
			 * sessionFactory.openSession(); session.beginTransaction();
			 * session.save(message); session.getTransaction().commit();
			 */
		
		callProcs();
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
 
        StoredProcedureQuery allemployees = s.createStoredProcedureQuery("getAllMessages", Message.class);
 
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
		 
        StoredProcedureQuery count = s.createStoredProcedureQuery("findEmployeeCountByDesignation");
        count.registerStoredProcedureParameter("emp_designation", String.class, ParameterMode.IN);
        count.registerStoredProcedureParameter("designation_count", Integer.class, ParameterMode.OUT);
 
        String param = "Lead";
        count.setParameter("emp_designation", param);
        count.execute();
 
        Integer employee_count = (Integer) count.getOutputParameterValue("designation_count");
        System.out.println("Employee count for designation= " + param + " is= " + employee_count);
 
        // Closing the session object.
        s.close();
	}
	
	@SuppressWarnings("unchecked")
	public  void latestCall()
	{
		ProcedureCall procedureCall = SessionUtil.getSession().createStoredProcedureCall("add_all_company_benefits");
		procedureCall.registerParameter("EMPLOYEE", Long.class, ParameterMode.IN);
		procedureCall.registerParameter("COMPANY", Long.class, ParameterMode.IN);
		procedureCall.getParameterRegistration("EMPLOYEE").bindValue(messages.getId());
		//procedureCall.getParameterRegistration("COMPANY").bindValue(company.getId());                
		ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
		ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
		@SuppressWarnings("rawtypes")
		List results = resultSetOutput.getResultList();
		for(Integer i=0;i<results.size();i++) {
		    Object[] objects = (Object[]) results.get(i);
		    System.out.println(objects[i]);
		    //LOGGER.info("The benefit is "+objects[1]);
		}
	}
}

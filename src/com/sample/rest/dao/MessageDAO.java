package com.sample.rest.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sample.rest.data.SessionUtil;
import com.sample.rest.model.Message;

public class MessageDAO
{
	public void addMessage(Message bean){
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        addMessage(session,bean);
        tx.commit();
        session.close();
        
    }
    
    private void addMessage(Session session, Message bean){
        Message Message = new Message();
        
        Message.setMessage(bean.getMessage());
        Message.setCreatedOn(bean.getCreatedOn());
        Message.setAmount(bean.getAmount());
        session.save(Message);
    }
    
    public List<Message> getMessages(){
        Session session = SessionUtil.getSession();    
        Query query = session.createQuery("from MessagesDetail");
        List<Message> Messages =  query.list();
        session.close();
        return Messages;
    }
 
    public int deleteMessage(int id) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        String hql = "delete from MessagesDetail where id = :id";
        Query query = session.createQuery(hql);
        query.setInteger("id",id);
        int rowCount = query.executeUpdate();
        System.out.println("Rows affected: " + rowCount);
        tx.commit();
        session.close();
        return rowCount;
    }
    
    public int updateMessage(int id, Message emp){
         if(id <=0)  
               return 0;  
         Session session = SessionUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "update MessagesDetail set message = :message, amount=:amount,createdOn=:createdOn where id = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id",id);
            query.setString("message",emp.getMessage());
            query.setDouble("amount",emp.getAmount());
            query.setDate("createdOn", emp.getCreatedOn());
            int rowCount = query.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            tx.commit();
            session.close();
            return rowCount;
    }
}

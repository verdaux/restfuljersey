package com.sample.rest.dao;

import java.util.List;

import org.hibernate.Query;
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
        Message message = new Message();
        
        message.setMessage(bean.getMessage());
        message.setCreatedOn(bean.getCreatedOn());
        message.setAmount(bean.getAmount());
        session.save(message);
    }
    
    public List<Message> getMessages(){
        Session session = SessionUtil.getSession();    
        Query query = session.createQuery(" from Message",Message.class);
        List<Message> Messages =  query.getResultList();
        session.close();
        return Messages;
    }
 
    public int deleteMessage(int msg_id) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        String hql = "delete from MessagesDetail where msg_id = :msg_id";
        Query query = session.createQuery(hql);
        query.setInteger("msg_id",msg_id);
        int rowCount = query.executeUpdate();
        System.out.println("Rows affected: " + rowCount);
        tx.commit();
        session.close();
        return rowCount;
    }
    
    public int updateMessage(int msg_ids, Message msgBean){
         if(msg_ids <=0)  
               return 0;  
         Session session = SessionUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "update MessagesDetail set message = :message, amount=:amount,createdOn=:createdOn where msg_id = :msg_ids";
            Query query = session.createQuery(hql);
            query.setInteger("msg_id",msg_ids);
            query.setString("message",msgBean.getMessage());
            query.setDouble("amount",msgBean.getAmount());
            query.setDate("createdOn", msgBean.getCreatedOn());
            int rowCount = query.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            tx.commit();
            session.close();
            return rowCount;
    }
}

package com.sample.rest.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;


@Entity(name="MessagesDetail")
@Table(name = "MessagesDetail")
@XmlRootElement
public class Message
{
	@Id
	//@Column(name = "id",unique=true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer msg_id;
	@Column
	private String message;
	@Column
	@Temporal(TemporalType.DATE)
	private Date createdOn;
	@Column
	private Double amount;



	public Integer getMsg_id()
	{
		return msg_id;
	}



	public void setMsg_id(Integer msg_id)
	{
		this.msg_id = msg_id;
	}



	public String getMessage()
	{
		return message;
	}



	public void setMessage(String message)
	{
		this.message = message;
	}



	public Date getCreatedOn()
	{
		return createdOn;
	}



	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}



	public Double getAmount()
	{
		return amount;
	}



	public void setAmount(Double amount)
	{
		this.amount = amount;
	}



	public Message()
	{
	}



	public Message(Integer msg_id, String message, Date createdOn, Double amount)
	{
		this.msg_id = msg_id;
		this.message = message;
		this.createdOn = createdOn;
		this.amount = amount;
	}



	@Override
	public String toString()
	{
		return "Message [msg_id=" + msg_id + ", message=" + message + ", createdOn=" + createdOn + ", amount=" + amount + "]";
	}
	
	
}

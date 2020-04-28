package com.sample.rest.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
public class Message
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String message;
	@Column
	private Date createdOn;
	@Column
	private Double amount;
	
	public Long getId()
	{
		return id;
	}



	public void setId(Long id)
	{
		this.id = id;
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



	public Message(Long id, String message, Date createdOn, Double amount)
	{
		this.id = id;
		this.message = message;
		this.createdOn = createdOn;
		this.amount = amount;
	}



	@Override
	public String toString()
	{
		return "Message [id=" + id + ", message=" + message + ", createdOn=" + createdOn + ", amount=" + amount + "]";
	}
	
	
}

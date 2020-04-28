package com.sample.rest.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
public class Message
{
	@Id
	private Long id;
	private String message;
	private Date createdOn;
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
	
	
}

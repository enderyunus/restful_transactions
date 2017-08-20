package org.ender.webservices.RestFul_Transactions.Models;

import java.util.Calendar;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction{
	
	private Double amount;
	private String recipient;	
	private String sender;
	
	private UUID transactionId;
	private Calendar transactionTime;

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public UUID getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}
	public Calendar getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Calendar transactionTime) {
		this.transactionTime = transactionTime;
	}


}

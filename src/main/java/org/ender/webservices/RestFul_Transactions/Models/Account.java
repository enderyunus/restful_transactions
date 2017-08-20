package org.ender.webservices.RestFul_Transactions.Models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account {
	
	private String username;
	private Double balance;
	//private Date date;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}


}

package org.ender.webservices.RestFul_Transactions.Exceptions;

public class AccountException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exceptionMessage="Exceptio ";
	
	public AccountException(){}
	
	public void setExceptionMessage(String reason){
		exceptionMessage+=reason;
	}
	
	public String getExceptionMessage()
	{
		return exceptionMessage;
	}
			
			
}

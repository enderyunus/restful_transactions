package org.ender.webservices.RestFul_Transactions.Exceptions;


public class TransactionException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exceptionMessage="Exception: ";
	
	public TransactionException(){}
	
	public void setExceptionMessage(String reason){
		exceptionMessage+=reason;
	}
	
	public String getExceptionMessage()
	{
		return exceptionMessage;
	}
			
			
}

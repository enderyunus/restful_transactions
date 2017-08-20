package org.ender.webservices.RestFul_Transactions.Enums;

public enum TransactionField {

	RECIPIENT("recipient"),
	SENDER("sender"),
	AMOUNT("amount"),
	UUID("uuid"),
	DATE("date"),
	NONE("none");
	
	String fieldName;
	
	private TransactionField(String fieldName )
	{
		this.fieldName=fieldName;
	}
	
	public String getValue()
	{
		return fieldName;
	}
}

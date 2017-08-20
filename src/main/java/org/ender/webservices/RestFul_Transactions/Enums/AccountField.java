package org.ender.webservices.RestFul_Transactions.Enums;

public enum AccountField {
	BALANCE("balance"),
	USERNAME("username"),
	NONE("none");
	
	private String fieldName;
	
	private AccountField(String fieldName )
	{
		this.fieldName=fieldName;
	}
	
	public String getValue()
	{
		return this.fieldName;
	}

}

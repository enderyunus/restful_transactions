package org.ender.webservices.RestFul_Transactions.Enums;

public enum SortType {

	ASC("ASC"),
	DESC("DESC"),
	NONE("NONE");
	
	private String sortType;
	
	private SortType(String sortType )
	{
		this.sortType=sortType;
	}
	
	public String getValue()
	{
		return sortType;
	}
}

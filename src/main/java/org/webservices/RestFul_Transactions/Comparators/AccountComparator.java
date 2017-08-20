package org.webservices.RestFul_Transactions.Comparators;

import java.util.Comparator;

import org.ender.webservices.RestFul_Transactions.Enums.AccountField;
import org.ender.webservices.RestFul_Transactions.Models.Account;

public class AccountComparator implements Comparator<Account> {
  
	private AccountField sf=AccountField.NONE;
	
	public int compare(Account a1, Account a2) {
    	if(sf.equals(AccountField.BALANCE))
    	{
	        if(a1.getBalance()>a2.getBalance())
	        	return 1;
	        else if(a2.getBalance()>a1.getBalance())
	        	return -1;
	        else
	        	return 0;
    	}
    	else if(sf.equals(AccountField.USERNAME))
    	{
	       return a1.getUsername().compareTo(a2.getUsername());
    	}
    	return 0;
    }
    public void setSortField(AccountField af)
    {
    	this.sf=af;
    }
    public AccountField getSortField()
    {
    	return this.sf;
    }
}


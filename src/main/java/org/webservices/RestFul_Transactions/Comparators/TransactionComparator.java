package org.webservices.RestFul_Transactions.Comparators;

import java.util.Comparator;

import org.ender.webservices.RestFul_Transactions.Enums.TransactionField;
import org.ender.webservices.RestFul_Transactions.Models.Transaction;

public class TransactionComparator implements Comparator<Transaction> {
	
	private TransactionField sf=TransactionField.NONE;
	
    public int compare(Transaction t1, Transaction t2) {
    	if(sf.equals(TransactionField.AMOUNT))
    	{
	        if(t1.getAmount()>t2.getAmount())
	        	return 1;
	        else if(t2.getAmount()>t1.getAmount())
	        	return -1;
	        else
	        	return 0;
    	}
    	else if(sf.equals(TransactionField.DATE))
    	{
	       return t2.getTransactionTime().compareTo(t1.getTransactionTime());
    	}
    	else if(sf.equals(TransactionField.RECIPIENT)){
 	       return t1.getRecipient().compareTo(t2.getRecipient());
    	}
    	else if(sf.equals(TransactionField.SENDER)){
  	       return t1.getSender().compareTo(t2.getSender());
     	}
    	else if(sf.equals(TransactionField.UUID)){
   	       return t1.getTransactionId().compareTo(t2.getTransactionId());
      	}
    	return 0;  	
    }
    
    public void setSortField(TransactionField tf)
    {
    	System.out.println("sortFieldSet");
    	this.sf=tf;
    }
    public TransactionField getSortField()
    {
    	return this.sf;
    }
}

package org.ender.webservices.RestFul_Transactions.Services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.ender.webservices.RestFul_Transactions.Enums.SortType;
import org.ender.webservices.RestFul_Transactions.Enums.TransactionField;
import org.ender.webservices.RestFul_Transactions.Enums.OperationType;
import org.ender.webservices.RestFul_Transactions.Exceptions.TransactionException;
import org.ender.webservices.RestFul_Transactions.Models.Account;
import org.ender.webservices.RestFul_Transactions.Models.Transaction;
import org.webservices.RestFul_Transactions.Comparators.*;


public class  TransactionService {

	private static HashMap<UUID,Transaction> transactions =new HashMap<UUID,Transaction>();
	
	public TransactionService(){
	}
	
	public static boolean addTransaction(Transaction t){
	
		Transaction curTransaction=new Transaction();
		transactions.put(curTransaction.getTransactionId(), curTransaction);
		return true;
	}


	//transactions by amount filter
	public ArrayList<Transaction> listTransactions(String recipient, String sender, 
			Double minAmount, Double maxAmount,String start, String end,
			String sortType,String sortField) {
	
		SortType st=SortType.NONE;
		
		for(SortType s:SortType.values())
		{
			if(s.getValue().equals(sortType))
			{	
				st=s;
				break;
			}
		}
				
		TransactionField sf=TransactionField.NONE;
		for(TransactionField t:TransactionField.values())
		{
			if(t.getValue().equals(sortField))
			{	
				sf=t;
				break;
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		Calendar calStart =null;
		if(start!=null)
		{
			try{
				Date begin = sdf.parse(start);
				calStart = Calendar.getInstance();
				calStart.setTime(begin);
			}
			catch(Exception e)
			{
			}
		}
		
		Calendar calEnd =null;
		if(end!=null)
		{
			try{
				Date finish = sdf.parse(end);
				calEnd = Calendar.getInstance();
				calEnd.setTime(finish);
			}
			catch(Exception e)
			{
			}
		}

		ArrayList<Transaction> resultList=new ArrayList<Transaction>() ;
		for(Transaction t: transactions.values())
		{
			if(recipient!=null && !recipient.equals(t.getRecipient()))
				continue;
			else if(sender!=null && !sender.equals(t.getSender()))
				continue;
			else if(minAmount!=null && !(minAmount<t.getAmount()))
				continue;
			else if(maxAmount!=null && !(maxAmount>t.getAmount()))
				continue;
			else if(start!= null && (calStart.compareTo(t.getTransactionTime())!=-1))
				continue;
			else if(end!= null && (calEnd.compareTo(t.getTransactionTime())!=1))
				continue;
			resultList.add(t);
		}
		if(!st.equals(SortType.NONE))
		{
			sortResult(resultList,st,sf);
		}
		return resultList;

	}

	
	//get single transaction
	public Transaction getSingleTransaction(String id){
		UUID uuid= UUID.fromString(id);
		try{
			trExistsCheck(uuid);
		}
		catch (TransactionException tr){
			System.out.println(tr.getExceptionMessage());
		}
		return transactions.get(uuid);
	}

	public boolean sendMoney(Transaction curTransaction) {
			
		try{
			trFieldsCheck(curTransaction, OperationType.TRANSFER);
			
			Account sender=AccountService.getAccounts().get(curTransaction.getSender());
			Account recipient=AccountService.getAccounts().get(curTransaction.getRecipient());
			
			sender.setBalance(sender.getBalance()-curTransaction.getAmount());
			recipient.setBalance(recipient.getBalance()+curTransaction.getAmount());
			
			curTransaction.setTransactionId(UUID.randomUUID());
			curTransaction.setTransactionTime(Calendar.getInstance());
		
			transactions.put(curTransaction.getTransactionId(), curTransaction);
			return true;

		}
		catch(TransactionException te){			
			System.out.println(te.getExceptionMessage());
			return false;
		}
	}

	public boolean makeDeposit(Transaction curTransaction) {
		
		
		try{

			trFieldsCheck(curTransaction, OperationType.DEPOSIT);		
			Account recipient=AccountService.getAccounts().get(curTransaction.getRecipient());			
		
			double amount=curTransaction.getAmount();		
			recipient.setBalance(recipient.getBalance()+amount);
			
			curTransaction.setTransactionId(UUID.randomUUID());
			curTransaction.setTransactionTime(Calendar.getInstance());
	
			transactions.put(curTransaction.getTransactionId(), curTransaction);
			return true;		
		}
		catch(TransactionException te){			
			System.out.println(te.getExceptionMessage());
			return false;
		}
		
	}

	public boolean withdraw(Transaction curTransaction) {
					
		try{

			trFieldsCheck(curTransaction, OperationType.WITHDRAW);		
		
			double amount=curTransaction.getAmount();

			Account sender=AccountService.getAccounts().get(curTransaction.getSender());
			sender.setBalance(sender.getBalance()-amount);
			
			curTransaction.setTransactionId(UUID.randomUUID());
			curTransaction.setTransactionTime(Calendar.getInstance());
			transactions.put(curTransaction.getTransactionId(), curTransaction);
			
			return true;		
		}	
		catch(TransactionException te){			
			System.out.println(te.getExceptionMessage());
			return false;
		}
		
	}
	
	private void sortResult(ArrayList<Transaction> a, SortType sorter, TransactionField tf)
	{
		TransactionComparator comparator=new TransactionComparator();
		comparator.setSortField(tf);
		if(sorter.equals(SortType.ASC))
			Collections.sort(a, Collections.reverseOrder(comparator));
		else if(sorter.equals(SortType.DESC))
			Collections.sort(a, comparator);
	}
	
	private boolean trExistsCheck(UUID uuid) throws TransactionException{
		if(transactions.containsKey(uuid))
			return true;
		else{	
			TransactionException tr=new TransactionException();
			tr.setExceptionMessage("Transaction with uuid+"+uuid+ "can't be found");
			throw tr;
		}
	}
	
	private boolean trFieldsCheck(Transaction t, OperationType o) throws TransactionException
	{
		if(!o.equals(OperationType.DEPOSIT) && !AccountService.getAccounts().containsKey(t.getSender()))
		{
			TransactionException te_sender= new TransactionException();
			te_sender.setExceptionMessage("Sender: "+t.getSender() +"doesn't have an account in the system");
			throw te_sender;
		}
		else if(!o.equals(OperationType.WITHDRAW) && !AccountService.getAccounts().containsKey(t.getRecipient()))
		{
			TransactionException te_recipient= new TransactionException();
			te_recipient.setExceptionMessage("Recipient: "+t.getRecipient() +"doesn't have an account in the system");
			throw te_recipient;
		}
		else if(t.getAmount()==null || t.getAmount()<=0)
		{
			TransactionException te_recipient= new TransactionException();
			te_recipient.setExceptionMessage("Amount of the transaction can't be empty and has to be bigger than 0.0");
			throw te_recipient;
		}
		

		if(!o.equals(OperationType.DEPOSIT))
		{
			Account sender=AccountService.getAccounts().get(t.getSender());
			 if(!o.equals(OperationType.DEPOSIT) && sender.getBalance()<t.getAmount())
				{
					TransactionException te_amount =new TransactionException();
					te_amount.setExceptionMessage("The amount of transaction can't be bigger than balance of sender");
					throw  te_amount;
				}
		}

		return true;
		
	}
}

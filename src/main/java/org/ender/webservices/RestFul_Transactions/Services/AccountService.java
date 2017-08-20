package org.ender.webservices.RestFul_Transactions.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.webservices.RestFul_Transactions.Comparators.AccountComparator;
import org.ender.webservices.RestFul_Transactions.Enums.*;
import org.ender.webservices.RestFul_Transactions.Exceptions.AccountException;
import org.ender.webservices.RestFul_Transactions.Models.Account;

public class AccountService {
	
	private static HashMap<String,Account> accounts=new HashMap<String, Account>() ;
	
	public AccountService(){
		//accounts=;
	}
	
	//return all accounts
	public static HashMap<String,Account> getAccounts()
	{
		return accounts;
	}
	
	
	//get single account with its ID

	public Account getSingleAccount(String username)
	{		
		try{
			System.out.println("sss" +username);
			existCheck(username);
			System.out.println("vv"+username);

		}
		catch (AccountException ae){
		}
		return accounts.get(username);
	}
	
	//list accounts by filtering their balance
	public ArrayList<Account> listAccounts(Double min, Double max, String sort,String sortField)
	{
		SortType st=SortType.NONE;
		for(SortType s:SortType.values())
		{
			if(s.getValue().equals(sort))
			{	
				st=s;
				break;
			}
		}
		
		AccountField sf=AccountField.NONE;		
		for(AccountField a:AccountField.values())
		{
			if(a.getValue().equals(sortField))
			{	
				sf=a;
				break;
			}
		}  
		
		ArrayList<Account> resultList=new ArrayList<Account>() ;
		for(Account a: accounts.values())
		{
			double curBalance=a.getBalance();
			if(min!= null && min>curBalance)
				continue;
			else if(max!= null && max<curBalance)
				continue;
			resultList.add(a);
		}

		sortResult(resultList,st,sf);
		return resultList;
	}

	public boolean addAccount(Account a) {

		try{
			addUserCheck(a);
			accounts.put(a.getUsername(), a);
			return true;
		}
		catch (AccountException ae){
			return false;
		}
	}
	
	public boolean deleteAccount(String username) throws AccountException
	{		
		try{
			existCheck(username);
			accounts.remove(username);
			return true;
		}
		catch (AccountException ae){
			return false;
		}
	}
	
	public boolean editAccount(Account a) 
	{
		try{
			existCheck(a.getUsername());
			balanceCheck(a.getBalance());
			accounts.replace(a.getUsername(), a);
			return true;
		}
		catch (AccountException ae){
			return false;
		}
	}
	
	private void sortResult(ArrayList<Account> a, SortType sorter,AccountField sf)
	{
		AccountComparator ac = new AccountComparator();
		ac.setSortField(sf);
		if(sorter.equals(SortType.ASC))
			Collections.sort(a, ac);
		else if(sorter.equals(SortType.DESC))
			Collections.sort(a, Collections.reverseOrder(ac));
	}
	
	
	
	private boolean addUserCheck(Account a) throws AccountException
	{
		if(a.getUsername()==null || "".equals(a.getUsername()))
		{
			 AccountException ae=new AccountException();
			 ae.setExceptionMessage("The account for can't be added. Username can't be empty or null.");
			 throw ae; 
		}
		else if(accounts.containsKey(a.getUsername()))
		{
			 AccountException ae=new AccountException();
			 ae.setExceptionMessage("The account for can't be added. There is already an account for: "+a.getUsername());
			 throw ae; 
		}
		balanceCheck(a.getBalance());
		return true;
	}
	
	private boolean existCheck(String u) throws AccountException
	{
		if(!accounts.containsKey(u))
		{
			 AccountException ae=new AccountException();
			 ae.setExceptionMessage("The user with username: "+u+" doesn't exist.");
			 throw ae; 
		}
		else 
			return true;
	}
	private boolean balanceCheck(Double b) throws AccountException
	{
		if(b==null || b<=0)
		{			 
			AccountException ae=new AccountException();
			ae.setExceptionMessage("Balance value should be a valid number greater than zero.");
			throw ae; 		
		}
		return true;
	}
	
}

package org.ender.webservices.RestFul_Transactions.Resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import org.ender.webservices.RestFul_Transactions.Models.Account;
import org.ender.webservices.RestFul_Transactions.Models.Transaction;
import org.ender.webservices.RestFul_Transactions.Services.AccountService;
import org.ender.webservices.RestFul_Transactions.Services.TransactionService;

	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	public class UserResources {
				
		TransactionService transactionService=new TransactionService();
		AccountService accountService= new AccountService();

		@GET
		@Path("/{username}/account")
		@Produces(MediaType.APPLICATION_JSON)
		public Account getSingle(@PathParam("username") String username) {
	    	try{
	    		return accountService.getSingleAccount(username);
	    	}
	    	catch(Exception e)
	    	{
	    		return new Account();
	    	}
	    }
			
		@GET
		@Path("/{username}/transactions")
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<Transaction> getTransactionsByUserAndAmount(@PathParam("username") String username, 
				@QueryParam("minAmt") Double minAmt, @QueryParam("maxAmt") Double maxAmt, 
				@QueryParam("minDate") String minDate,@QueryParam("maxDate") String maxDate,
				@QueryParam("sortField") String sortField,
				@QueryParam("sort") String sort) {
			ArrayList<Transaction> list = new ArrayList<Transaction>();
			
			
			list.addAll(transactionService.listTransactions(username,null,minAmt,maxAmt,minDate,maxDate,sort,sortField));	
			list.addAll(transactionService.listTransactions(null,username,minAmt,maxAmt,minDate,maxDate,sort,sortField));	

			return list;

	    }
		
		
		@POST
		@Path("/{username}/sendMoney")
		@Produces(MediaType.TEXT_PLAIN)
		public boolean sendMoney(Transaction t, @PathParam("username") String username)
		{
			if(username==null ||username.equals(t.getSender()))
			{
				try{
					return transactionService.sendMoney(t);
				}
				catch(Exception e)
				{
					System.out.println(e);
					return false;
				}
			}
			else
			{
				System.out.println("Invalid transaction request.");
				return false;
			}
		}
		
		@POST
		@Path("/{username}/deposit")
		@Produces(MediaType.TEXT_PLAIN)
		public boolean makeDeposit(Transaction t, @PathParam("username") String username)
		{			
			if(username==null || username.equals(t.getRecipient()))
			{
				try{
					return transactionService.makeDeposit(t);
				}
				catch(Exception e)
				{
					System.out.println(e);
					return false;
				}
			}
			else
			{
				System.out.println("Invalid transaction request.");
				return false;
			}
		}
		
		@POST
		@Path("/{username}/withdraw")
		@Produces(MediaType.TEXT_PLAIN)
		public boolean withdraw(Transaction t, @PathParam("username") String username)
		{
			if(username==null ||username.equals(t.getSender()))
			{
					return transactionService.withdraw(t);		
			}
			else
			{
				System.out.println("Invalid withdrawal request.");
				return false;	
			}
			
		}
	

}

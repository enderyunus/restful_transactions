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

import org.ender.webservices.RestFul_Transactions.Models.Transaction;
import org.ender.webservices.RestFul_Transactions.Services.TransactionService;


	@Path("/transactionsManagement")
	@Consumes(MediaType.APPLICATION_JSON)
	public class TransactionResources {
				
		TransactionService transactionService=new TransactionService();

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<Transaction> listTransactions(@QueryParam("minAmt") Double minAmt,@QueryParam("maxAmt") Double maxAmt,
				@QueryParam("minDate") String minDate,@QueryParam("maxDate") String maxDate,
				@QueryParam("recipient") String rec,@QueryParam("sender") String snd,
				@QueryParam("sortField") String sortField,@QueryParam("sort") String sort) {
			
			
			ArrayList<Transaction> list = new ArrayList<Transaction>();
			list=transactionService.listTransactions(rec,snd,minAmt,maxAmt,minDate,maxDate,sort,sortField);	
			return list;
			
	    }
		
		@GET
		@Path("/{uuid}")
		@Produces(MediaType.APPLICATION_JSON)
		public Transaction listSingleTransaction(@PathParam("uuid") String uuid) {	
			return transactionService.getSingleTransaction(uuid);

		}
			
		@POST
		@Path("/makeTransaction")
		@Produces(MediaType.TEXT_PLAIN)
		public boolean sendMoney(Transaction t)
		{
			return transactionService.sendMoney(t);
		}
		
		@POST
		@Path("/addMoney")
		@Produces(MediaType.TEXT_PLAIN)
		public boolean makeDeposit(Transaction t)
		{
			return transactionService.makeDeposit(t);		
		}
		
		@POST
		@Path("/deleteMoney")
		@Produces(MediaType.TEXT_PLAIN)
		public boolean withdraw(Transaction t)
		{
			return transactionService.withdraw(t);
		
		}
}

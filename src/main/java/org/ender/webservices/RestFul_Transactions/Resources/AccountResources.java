package org.ender.webservices.RestFul_Transactions.Resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.ender.webservices.RestFul_Transactions.Exceptions.AccountException;
import org.ender.webservices.RestFul_Transactions.Models.Account;
import org.ender.webservices.RestFul_Transactions.Services.AccountService;

	@Path("/accountsManagement")
	@Consumes(MediaType.APPLICATION_JSON)
	public class AccountResources {
			
		AccountService accountService=new AccountService();
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<Account> getAll(@QueryParam("min") Double min, @QueryParam("max") Double max, 
				@QueryParam("sortField") String sortField, @QueryParam("sort") String sort) {			
			ArrayList<Account> list = new ArrayList<Account>();		
			list=accountService.listAccounts(min, max,sort,sortField);
			
			return list;
	    }
		
		@GET
		@Path("/{username}")
		@Produces(MediaType.APPLICATION_JSON)
		public Account getSingleAccount(@PathParam("username") String username) {
				return accountService.getSingleAccount(username);
	    }

		@POST
		@Produces(MediaType.TEXT_PLAIN)
		public boolean addAccount(Account a)
		{
				return accountService.addAccount(a);		
		}	
		@PUT
		@Produces(MediaType.TEXT_PLAIN)
		public boolean editAccount(Account a)
		{
			return accountService.editAccount(a);		

		}
		
		@DELETE
		@Produces(MediaType.TEXT_PLAIN)
		public boolean deleteAccount(Account a)
		{
			try{
				return accountService.deleteAccount(a.getUsername());	
			}
			catch(AccountException ae)
			{
				System.out.println(ae+":" +ae.getExceptionMessage());
				return false;
			}
		}

}

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;


import org.ender.webservices.RestFul_Transactions.Models.Account;
import org.ender.webservices.RestFul_Transactions.Models.Transaction;
import org.ender.webservices.RestFul_Transactions.Resources.AccountResources;
import org.ender.webservices.RestFul_Transactions.Resources.TransactionResources;
import org.junit.Test;

/*import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;*/

/**
 * Unit test for simple App.
 */
public class AppTest 
   // extends TestCase
{/*
    *//**
     * Create the test case
     *
     * @param testName name of the test case
     *//*
    public AppTest( String testName )
    {
        super( testName );
    }

    *//**
     * @return the suite of tests being tested
     *//*
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    *//**
     * Rigourous Test :-)
     *//*
    public void testApp()
    {
        assertTrue( true );
    }*/
	
	@Test
    public void accountOperations() {
		try {
			
			
			AccountResources ar= new AccountResources();
			TransactionResources tr=new TransactionResources();
			
			HashMap<String,Double> balances= new HashMap<String,Double>();
			
			Account userX= new Account();
			userX.setUsername("X");
			assertEquals("No balance new account should not be added",false,ar.addAccount(userX));
			userX.setBalance(Math.random()*(-10));
			assertEquals("Negative balance should not be added",false,ar.addAccount(userX));
			userX.setBalance(Math.random()*10);
			
			assertEquals("Make a valid new account with username X",true,ar.addAccount(userX));
			balances.put("X", userX.getBalance());
			Thread.sleep(10);

			Account userY= new Account();
			userY.setBalance(Math.random()*10);
			assertEquals("Invalid new account: no username",false,ar.addAccount(userY));
			userY.setUsername("Y");
			assertEquals("Make a valid new account",true,ar.addAccount(userY));
			balances.put("Y", userY.getBalance());

			Thread.sleep(10);

			Account userZ= new Account();
			userZ.setBalance(Math.random()*10);
			userZ.setUsername("Z");
			assertEquals("Valid new account",true,ar.addAccount(userZ));
			balances.put("Z", userZ.getBalance());

			
			
			Account userSameUsername= new Account();
			userSameUsername.setBalance(Math.random()*10);
			userSameUsername.setUsername("Z");
			assertEquals("Another account with same username should fail",false,ar.addAccount(userSameUsername));
			
			
			Account nonExistingUser=new Account();
			nonExistingUser.setUsername("user4");
			nonExistingUser.setBalance(1.0);

			assertEquals("No username with u",false,ar.deleteAccount(nonExistingUser));
			assertEquals("No username with u",false,ar.editAccount(nonExistingUser));


			assertEquals("GeTAll elements should return three",3,ar.getAll(null, null, null, null).size());
			

			ArrayList<Account> result =ar.getAll(null, null, "balance","ASC");
			
			
			assertEquals("Max values should match",true,Collections.max(balances.values()).equals(result.get(2).getBalance()));	
			assertEquals("Min values should match",true,Collections.min(balances.values()).equals(result.get(0).getBalance()));	


			result =ar.getAll(null, null, "balance","DESC");
			

			assertEquals("Max values should match",true,Collections.max(balances.values()).equals(result.get(0).getBalance()));	
			assertEquals("Min values should match",true,Collections.min(balances.values()).equals(result.get(2).getBalance()));	

			result =ar.getAll(null, null, "username","ASC");
			
			assertEquals("Last username has to be Z",userZ,result.get(2));	
			assertEquals("First username has to be X",userX,result.get(0));		

			result =ar.getAll(null, null, "username","DESC");
			
			assertEquals("First username has to be Z",userZ,result.get(0));	
			assertEquals("Last username has to be X",userX,result.get(2));	
		
			assertEquals("Filter check",1,ar.getAll(Collections.max(balances.values()), null, null, null).size());
			assertEquals("Filter check",3,ar.getAll(Collections.min(balances.values()), Collections.max(balances.values()),null, null).size());
			
			Account editItem=new Account();
			editItem.setUsername("userNonExistingYet");
			editItem.setBalance(11.0);
			assertEquals("Non existing user cant be edited",false,ar.editAccount(editItem));

			editItem.setUsername("X");
			assertEquals("Now user X has to be replaced with editItem",true,ar.editAccount(editItem));
			
			result=ar.getAll(null, null, null,null);
			assertEquals("X(object) is no more in the results",false,result.contains(userX));
			assertEquals("EditItem(onject is there)",true,result.contains(editItem));
			assertEquals("EditItem with name X has balance of 11.0",true,editItem.getBalance()==11.0);

			Transaction t1=new Transaction();
			t1.setSender("Z");
			t1.setAmount(Math.random()*userZ.getBalance());
			t1.setTransactionId(UUID.randomUUID());
			t1.setTransactionTime(Calendar.getInstance());
			
			assertEquals("Shouldnt be send without a recipient",false,tr.sendMoney(t1));
			
			double YbeforeT=userY.getBalance();
			double ZbeforeT=userZ.getBalance();

			t1.setRecipient("Y");
			assertEquals("Now a valid transaction",true,tr.sendMoney(t1));
			assertEquals("Amount check after transaction",true,userY.getBalance()==YbeforeT+t1.getAmount());

			assertEquals("Amount check after transaction",true,userZ.getBalance()==ZbeforeT-t1.getAmount());
			
			Thread.sleep(10);

			Transaction t2=new Transaction();
			t2.setSender("Z");
			t2.setRecipient("Y");
			t2.setAmount(userZ.getBalance()+0.01);
			t2.setTransactionId(UUID.randomUUID());
			t2.setTransactionTime(Calendar.getInstance());

			assertEquals("Not a valid transaction due to amount",false,tr.sendMoney(t2));
			t2.setAmount(userZ.getBalance()-userZ.getBalance()/1.5);
			assertEquals("Valid transaction",true,tr.sendMoney(t2));

			Thread.sleep(10);

			Transaction t3=new Transaction();
			t3.setSender("Y");
			t3.setRecipient("Z");
			t3.setTransactionId(UUID.randomUUID());
			t3.setTransactionTime(Calendar.getInstance());
			
			assertEquals("Transaction without an amount-invalid",false,tr.sendMoney(t3));	
			t3.setAmount(0.0);
			assertEquals("0 amount is also invalid",false,tr.sendMoney(t3));	
			t3.setAmount(-0.1);
			assertEquals("Negative amount is also invalid",false,tr.sendMoney(t3));	
			
			t3.setAmount(Math.random()*userY.getBalance());			
			assertEquals("Amount added->valid",true,tr.sendMoney(t3));		
	
			ArrayList<Transaction> tResult=tr.listTransactions(null, null, null, null, null, null, null, null);
			assertEquals("Total 3 trnsactions are made",true,tResult.size()==3);			

			tResult=tr.listTransactions(0.0,15.0 , "01-01-2017", "01-08-2017", null, null, null, null);
			assertEquals("Date filter 1",true,tResult.size()==0);	
			tResult=tr.listTransactions(0.0,15.0 , "15-08-2017", "31-09-2017", null, null, null, null);
			assertEquals("Date filter 2",true,tResult.size()==3);	

			tResult=tr.listTransactions(null,null, "01-01-2017", "31-09-2017", "Y", null, null, null);
		

			assertEquals("In two transactions recipient was Y",true,tResult.size()==2);	
			assertEquals("In t1",true,tResult.contains(t1));	
			assertEquals("And in t2",true,tResult.contains(t2));	

			tResult=tr.listTransactions(0.0,15.0 , "01-01-2017", "30-09-2017", "Z", "Y", null, null);
			assertEquals("One transaction from Y to Z",true,tResult.size()==1);	
			assertEquals("It was t3",t3,tResult.get(0));	
			

			tResult=tr.listTransactions(0.0,15.0 , "01-01-2017", "30-09-2017", null, null, "date", "DESC");

			assertEquals("t1 is the first transaction by date",t1,tResult.get(2));	
			assertEquals("t3 is the last transaction by date",t3,tResult.get(0));	
			
			tResult=tr.listTransactions(0.0,15.0 , "01-01-2017", "30-09-2017", null, null, "date", "ASC");

			assertEquals("Reverse date check",t1,tResult.get(0));	
			assertEquals("Reverse date check",t3,tResult.get(2));	
			
			Transaction falseSenderTransaction=new Transaction();
			createTransaction(falseSenderTransaction, "None", "Y",1.0);
			
			assertEquals("Sender name is invalid",false,tr.sendMoney(falseSenderTransaction));
			
			Transaction falseRecipientTransaction=new Transaction();
			createTransaction(falseRecipientTransaction, "Z", "NoName",1.0);		
			
			assertEquals("Recipient name is invalid",false,tr.sendMoney(falseRecipientTransaction));
			
			System.out.println(userY.getBalance()+"//"+userZ.getBalance());

			
			System.out.println(userY.getBalance()+"//"+userZ.getBalance());

			
			Transaction balanceNotEnoughTr= new Transaction();
			ar.getSingleAccount("Z");
			createTransaction(balanceNotEnoughTr, "Z", "Y",ar.getSingleAccount("Z").getBalance()+0.1);
			
			assertEquals("Balance not enough transction",false,tr.sendMoney(balanceNotEnoughTr));
			
			Transaction balanceFixedTr= new Transaction();
			createTransaction(balanceFixedTr, "Z", "Y",ar.getSingleAccount("Z").getBalance()/6);
			assertEquals("Balance problem fixed",true,tr.sendMoney(balanceFixedTr));
			
			Transaction validDeposit= new Transaction();
			
			System.out.println(userY.getBalance()+"//"+userZ.getBalance());

			
			Double beforeDeposit=ar.getSingleAccount("Y").getBalance();
			Double depAmount=Math.random()*10;
			createTransaction(validDeposit, null, "Y",depAmount);
			assertEquals("A valid deposit",true,tr.makeDeposit(validDeposit));
			Double afterDeposit=ar.getSingleAccount("Y").getBalance();
			assertEquals("Amount check after Deposit",true,afterDeposit==beforeDeposit+depAmount);
			
			System.out.println(userY.getBalance()+"//"+userZ.getBalance());
			
			Transaction validWithdraw= new Transaction();
			
			Double beforeWithdraw=ar.getSingleAccount("Y").getBalance();
			System.out.println(beforeWithdraw);
			createTransaction(validWithdraw, "Y", null,ar.getSingleAccount("Y").getBalance()/6);	
			assertEquals("Valid Deposit",true,tr.withdraw(validWithdraw));
			
			Double afterWithdraw=ar.getSingleAccount("Y").getBalance();
			assertEquals("Amount check after withdraw",true,beforeWithdraw==afterWithdraw+validWithdraw.getAmount());
			
			Transaction notEnoughMoney= new Transaction();		
			createTransaction(notEnoughMoney, "Z", null,ar.getSingleAccount("Z").getBalance()+0.5);	
			assertEquals("Not enough money withdraw->invalid",false,tr.withdraw(notEnoughMoney));
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createTransaction(Transaction t, String snd, String rec,Double amt)
	{
		
		t.setSender(snd);
		t.setRecipient(rec);
		t.setTransactionId(UUID.randomUUID());
		t.setAmount(amt);
		t.setTransactionTime(Calendar.getInstance());
	}
}

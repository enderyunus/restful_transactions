package org.ender.webservices.RestFul_Transactions;

import java.util.Arrays;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.ender.webservices.RestFul_Transactions.Resources.AccountResources;
import org.ender.webservices.RestFul_Transactions.Resources.TransactionResources;
import org.ender.webservices.RestFul_Transactions.Resources.UserResources;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args)
    {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                String.join(",", Arrays.asList(AccountResources.class.getCanonicalName(),
                        TransactionResources.class.getCanonicalName(),
                        UserResources.class.getCanonicalName())));
            try {
				jettyServer.start();
	            jettyServer.join();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         finally {
            jettyServer.destroy();
         }
    }
}

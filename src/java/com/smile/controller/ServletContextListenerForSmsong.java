package com.smile.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;

/**
 * Application Lifecycle Listener implementation class ServletContextListenerForSmsong
 *
 */
public class ServletContextListenerForSmsong implements ServletContextListener {

    public static HashMap<HttpSession,Connection> sessionMap = null;
    
    public ServletContextListenerForSmsong() {
        sessionMap = new HashMap<HttpSession,Connection>();
        System.out.println("ServletContextListenerForSmsong constructed.");
    }

    public void contextDestroyed(ServletContextEvent arg0)  {
    	
    	System.out.println("Size of HashMap= "+sessionMap.size());
    	
        Iterator<HttpSession> itr = sessionMap.keySet().iterator();
        while (itr.hasNext()) {
            HttpSession session = itr.next();
            try
            {
                Connection JdbcConnection = sessionMap.get(session);
                if (JdbcConnection != null)
                {
                        System.out.println("JdbcConnection is not null!!");
                        JdbcConnection.close();
                }
                // session has been invalidated when web container is being shutdown
                // before contextDestroued() being executed
                // session.invalidate();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        sessionMap.clear();

        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = (Driver)drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Database smsong disconnected !!");
    	System.out.println("ServletContextListenerForSmsong destroyed.");
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println("ServletContextListenerForSmsong initialized.");
    }
	
}

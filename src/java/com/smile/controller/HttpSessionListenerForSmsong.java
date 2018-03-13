package com.smile.controller;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

public class HttpSessionListenerForSmsong implements HttpSessionListener  {
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
            HttpSession session = event.getSession();

            // Set timeout of inactive to 10 minutes.
            // means sessionDestroyed will be called if it has been inactive more than 10 minutes
            session.setMaxInactiveInterval(20*60);	// 20 minutes
            //

            ServletContextListenerForSmsong.sessionMap.put(session,null);
            System.out.println("HttpSessionListenerForSmsong->HashMap size() up to " + ServletContextListenerForSmsong.sessionMap.size());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
            System.out.println("HttpSessionListenerForSmsong->HashMap size() before remove " + ServletContextListenerForSmsong.sessionMap.size());
            try
            {
                HttpSession session = event.getSession();
                if (session != null)
                {
                    Connection JdbcConnection = ServletContextListenerForSmsong.sessionMap.get(session);
                    if (JdbcConnection != null)
                    {
                            System.out.println("HttpSessionListenerForSmsong->JdbcConnection is not null!!");
                            JdbcConnection.close();
                    }
                    ServletContextListenerForSmsong.sessionMap.remove(session);	
                    System.out.println("HttpSessionListenerForSmsong->HashMap size() down to " + ServletContextListenerForSmsong.sessionMap.size());
                }
                else
                {
                    System.out.println("HttpSessionListenerForSmsong->session is null.");
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
	}
}

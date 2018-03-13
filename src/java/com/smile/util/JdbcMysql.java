package com.smile.util;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.smile.controller.ServletContextListenerForSmsong; 

public class JdbcMysql
{
    private static boolean driverLoaded = false;

    public static void registerJdbcDrive() {
        try { 
            // load driver  
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            driverLoaded = true;
        }
        // catch(ClassNotFoundException e) 
        catch(Exception e) { 
            driverLoaded = false;
            System.out.println("Failed to register com.mysql.Driver !!");
         } // sqlexception
    }

    public static Connection ConnectJdbc()
    {
        Connection con = null;
        // RDS of Amazon AWS 
        // String url = new String("jdbc:mysql://smsong.c56yuu3xozbt.us-east-1.rds.amazonaws.com:3306");
        String url = new String("jdbc:mysql://localhost:3306");
        System.out.println(url);
        try {
            if (!driverLoaded)
            {
                registerJdbcDrive();
            }
            if (driverLoaded)
            {
                // String username = "chaolee";	// for RDS of Amazon AWS
                String username = "root";	// for local host
                String password = "86637971";
                con = DriverManager.getConnection(url+"/smsong?useUnicode=true&characterEncoding=utf-8",
                                username,password) ;
            }

        } catch(Exception e) { 
            e.printStackTrace();
            System.out.println("Failed to connect !!"); 
            return null;
        }

        return(con);
    }

    public static void closeConnection(Connection con) {
        try {
            if (con!=null) {
                    con.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Connection getStoredConnection(HttpServletRequest request, boolean createYN)
    {
        if (ServletContextListenerForSmsong.sessionMap == null)
        {
            return null;
        }

        HttpSession session = request.getSession();
        System.out.println("HashMap size() = "+ServletContextListenerForSmsong.sessionMap.size());
        Connection dbConn = ServletContextListenerForSmsong.sessionMap.get(session);
        if (dbConn == null)
        {
            // no database connection
            System.out.println("No database connection for this session");
            if (createYN)
            {
                // needed to create a new connection
                System.out.println("Creating a new database connection .....");
                dbConn = ConnectJdbc();
                if (dbConn != null)
                {
                    // save database connection to HashMap
                    ServletContextListenerForSmsong.sessionMap.put(session, dbConn);
                }
                else
                {
                    // failed to create a new connection
                    System.out.println("Failed to create a new database connection.");
                }
            }
        }

        System.out.println("After getStoredConnection-> HashMap size() = "+ServletContextListenerForSmsong.sessionMap.size());

        return dbConn;
    }
}

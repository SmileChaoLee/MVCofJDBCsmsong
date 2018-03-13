/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.controller;

import com.smile.util.JdbcMysql;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 *
 * @author chaolee
 */
@WebServlet(name = "LoginForRESTfulServlet", urlPatterns = {"/LoginForRESTfulServlet"})
public class LoginForRESTfulServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("LoginForRESTfulServlet.htm .....");
        
        JSONObject jsonObject = new JSONObject();
        boolean isConnectedToJDBC = true;
        String outJSON = "";
        String uName = "";
        String uEmail = "";
        String uState = "";
        
        // if database connection does not exist then create a new one
        Connection dbConn = JdbcMysql.getStoredConnection(request,true);
        if (dbConn == null) {
            System.out.println("LoginForRESTfulServlet.htm -> dbConn is null. ");
            isConnectedToJDBC = false;
        }
        else 
        {
            String name = request.getParameter("username");
            String pass = request.getParameter("password");

            if ( (name != null) && (pass != null) )
            {
                if ( (!name.isEmpty())) {
                    name = name.trim();
                    pass = pass.trim();
                    try
                    {
                        String sqlString = "select * from user where u_name = ? and u_pwd = ?";
                        PreparedStatement st = dbConn.prepareStatement(sqlString);
                        st.setString(1, name);
                        st.setString(2, pass);
                        ResultSet rs = st.executeQuery();

                        uName = "Unknown user or wrong password.";
                        uEmail = "";
                        uState = "";
                        if (rs.next())
                        {
                            // succeeded to login 
                            uName = rs.getString("u_name");
                            System.out.println("uName = " + uName);
                            uEmail = rs.getString("U_email");
                            System.out.println("uEmail = " +uEmail);
                            uState = rs.getString("u_state");
                            System.out.println("uState = " + uState);
                        }
                    }
                    catch (SQLException ex)
                    {
                        System.out.println(ex.toString());
                        uName = "SQL Exception happend.";
                        uEmail = "";
                        uState = "";
                    }
                }
                else
                {
                    // empty user name of password
                    uName = "User name can not be blank.";
                    uEmail = "";
                    uState = "";
                }
            }
            else
            {
                // empty user name of password
                uName = "User name and password must be inputed.";
                uEmail = "";
                uState = "";
            }
        }
        
        // json process
        try {
            jsonObject.put("isConnectedToJDBC", isConnectedToJDBC);
            jsonObject.put("userName", uName);
            jsonObject.put("userEmail", uEmail);
            jsonObject.put("userState", uState);
            outJSON = jsonObject.toString();

        } catch (Exception ex) {
            // JSON Exception happend
            ex.printStackTrace();
        }

        // writing
        // response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.println(outJSON);
        }
        //
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
